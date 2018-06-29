<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cncounter.util.string.StringNumberUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@include file="../basePath.jsp"%>
<%!

    public static final String randomPrefix = "random.";
    //
    public static String randomFileId(String origfileurl, String targetfilename){

        //
        String randomFileId = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date()) + Math.random();
        randomFileId = randomFileId.replace(".", "") + ".";
        //
        targetfilename = targetfilename.replace(randomPrefix, randomFileId); // 随机文件ID
        //
        return targetfilename;
    }
%>

<%
    //
    boolean autoDownload = false;
    //
    String origfileurl = request.getParameter("origfileurl");
    String targetfilename = request.getParameter("targetfilename");
    //
    if(StringNumberUtil.isEmpty(origfileurl)){
        origfileurl = "";
    } else {
        origfileurl = URLDecoder.decode(origfileurl, "UTF-8");
    }
    if(StringNumberUtil.isEmpty(targetfilename)){
        targetfilename = "";
    } else {
        targetfilename = URLDecoder.decode(targetfilename, "UTF-8");
        targetfilename = targetfilename.replace("?", "_").replace("/", "_");
    }
    // 如 random.zip
    if(targetfilename.startsWith(randomPrefix)){
        targetfilename = randomFileId(origfileurl, targetfilename); // 随机文件ID
    }
    //
    if(StringNumberUtil.notEmpty(origfileurl) && StringNumberUtil.notEmpty(targetfilename)){
        autoDownload = true;
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>代理下载 - CN计数-cncounter</title>
    <jsp:include page="/common/cssjs.jsp"></jsp:include>
</head>
<body>
    <jsp:include page="/common/header.jsp"></jsp:include>
    <div class="container-fluid">
        <div class="content_left">
            <p class="h1">代理VPN下载</p>
            <div>
                <form id="input_form" action="<%=basePath %>download/genfileurl.json" method="post">
                    <span>请输入需要下载的URL地址:</span>
                    <br/>
                    <textarea class="break-all" tabindex="3" id="origfileurl" name="origfileurl" rows="4" cols="36"><%=origfileurl%></textarea>
                    <br/>
                    自定义文件名:
                    <br/>
                    <input tabindex="4" name="targetfilename" value="<%=targetfilename%>" style="width: 100%;">
                    <br/>
                </form>
                <div style=" margin-top: 10px; ">
                    <button tabindex="4" id="btn_generate_link"
                            type="button" class="btn btn-primary"> 后台下载 </button>
                </div>
            </div>
            <div>
                <br/>
            </div>
            <div class="qrcode-jpeg-area">
                <div>
                    <h3>使用说明</h3>
                    <ol>
                        <li>输入下载URL</li>
                        <li>如果下载URL默认文件名不对，可以输入自定义文件名</li>
                        <li>点击后台下载</li>
                        <li>等待服务器,点击下载地址后面的链接</li>
                        <li>如果文件较大，或者下载速度慢,需要一段时间后链接才有文件(隔一会再试)</li>
                    </ol>
                </div>
                <span>下载地址:</span>
                 <a id="file_download_anchor" target="_blank">
                </a>
            </div>
        </div>
        <jsp:include page="/common/sidebar.jsp"></jsp:include>
    </div>
    <jsp:include page="/common/footer.jsp"></jsp:include>
    
    <script type="text/javascript">
        // 此处JS应该归拢收集
        $(function(){
            //
            var $btn_generate_link = $("#btn_generate_link");
            var $file_download_anchor = $("#file_download_anchor");
            var $input_form = $("#input_form");
            var $origfileurl = $("#origfileurl");
            var $targetfilename = $("input[name=targetfilename]");
            //
            var href = window.location.href;
            //
            //
            $btn_generate_link.bind("click", triggerDownloadFile);

            function triggerDownloadFile(e){
                //
                var origfileurl = $origfileurl.val();
                var targetfilename = $targetfilename.val();
                if(!origfileurl){
                    layer.msg("url不能为空!");
                    return;
                }
                //
                var url = $input_form.attr("action");
                var data = {
                    origfileurl : origfileurl
                    ,
                    targetfilename : targetfilename
                };

                var successCallback = function (message) {
                    var meta = message["meta"] || "";
                    var info = message["info"] || "";
                    layer.msg(info);
                    //
                    var downloadurl = meta["downloadurl"];
                    $file_download_anchor.attr("href", downloadurl);
                    $file_download_anchor.text(downloadurl);
                    // 循环探测. 是否已经成功
                    // var testURL = "/download/existsfile.json";
                    //
                    var delay = 2*1000;
                    window.setTimeout(function(){
                        detachFileExists(downloadurl, delay);
                    }, delay);
                };
                //
                var errorCallback = function (jqXHR, textStatus, errorThrown) {
                    // 把错误吃了
                    layer.msg("网络请求失败");
                };
                //
                postAjax(url, data, successCallback,errorCallback,1);
                //
            }
            //
            function detachFileExists(targetfilename, delay){
                //
                delay = delay || 2*1000;
                var url = "/download/existsfile.json";
                var data = {
                    targetfilename : targetfilename
                };

                var successCallback = function (message) {
                    var meta = message["meta"] || "";
                    var info = message["info"] || "";
                    var status = message["status"] || 0;
                    if(status){
                        layer.msg(info);
                        var downloadurl = meta["downloadurl"];
                        // 创建 iframe 下载文件?
                        downloadurl && window.open(downloadurl, "_blank");
                        //
                        return;
                    }
                    // 循环探测. 是否已经成功
                    window.setTimeout(function(){
                        detachFileExists(targetfilename, delay);
                    }, delay);
                };
                //
                var errorCallback = function (jqXHR, textStatus, errorThrown) {
                    // 把错误吃了
                    layer.msg("网络请求失败");
                };
                //
                postAjax(url, data, successCallback,errorCallback,1);
            };
            //
            <%
            if(autoDownload){
            %>
                window.setTimeout(function(){
                    triggerDownloadFile();
                }, 1000);
            <%
            }
            %>
        });
    </script>
</body>
</html>