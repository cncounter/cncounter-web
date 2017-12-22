<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>chrome.loadTimes()</title>
    <script type="text/javascript">
        var startTime = new Date();
    </script>

</head>
<body>
<h1>Chrome加载时间</h1>
<h2> - chrome.loadTimes()示例</h2>
<p>
    <a href="./checkBrowser.jsp" target="_blank">检测浏览器类型</a>
</p>
<table border="1">
    <thead>
    <tr>
        <th>说明</th>
        <th>字段</th>
        <th>值</th>
    </tr>
    </thead>
    <tbody>

    <tr>
        <td>requestTime</td>
        <td>requestTime</td>
        <td id="requestTime"></td>
    </tr>
    <tr>
        <td>startLoadTime</td>
        <td>startLoadTime</td>
        <td id="startLoadTime"></td>
    </tr>
    <tr>
        <td>commitLoadTime</td>
        <td>commitLoadTime</td>
        <td id="commitLoadTime"></td>
    </tr>
    <tr>
        <td>finishDocumentLoadTime</td>
        <td>finishDocumentLoadTime</td>
        <td id="finishDocumentLoadTime"></td>
    </tr>
    <tr>
        <td>finishLoadTime</td>
        <td>finishLoadTime</td>
        <td id="finishLoadTime"></td>
    </tr>
    <tr>
        <td>firstPaintTime</td>
        <td>firstPaintTime</td>
        <td id="firstPaintTime"></td>
    </tr>
    <tr>
        <td>firstPaintAfterLoadTime</td>
        <td>firstPaintAfterLoadTime</td>
        <td id="firstPaintAfterLoadTime"></td>
    </tr>
    <tr>
        <td>navigationType</td>
        <td>navigationType</td>
        <td id="navigationType"></td>
    </tr>
    <tr>
        <td>wasFetchedViaSpdy</td>
        <td>wasFetchedViaSpdy</td>
        <td id="wasFetchedViaSpdy"></td>
    </tr>
    <tr>
        <td>wasNpnNegotiated</td>
        <td>wasNpnNegotiated</td>
        <td id="wasNpnNegotiated"></td>
    </tr>
    <tr>
        <td>npnNegotiatedProtocol</td>
        <td>npnNegotiatedProtocol</td>
        <td id="npnNegotiatedProtocol"></td>
    </tr>
    <tr>
        <td>wasAlternateProtocolAvailable</td>
        <td>wasAlternateProtocolAvailable</td>
        <td id="wasAlternateProtocolAvailable"></td>
    </tr>
    <tr>
        <td>connectionInfo</td>
        <td>connectionInfo</td>
        <td id="connectionInfo"></td>
    </tr>

    </tbody>

</table>


<div>
    <h3>返回结果</h3>
    <textarea id="resultJSON" rows="12" cols="48"></textarea>
</div>

<div>
    <h3>使用示例</h3>
    <textarea id="sample" rows="12" cols="48"></textarea>
</div>


<script>

    // 该函数参见: http://www.cncounter.com/static/js/cncounter-util.js?v=1
    // 遍历对象属性
    function foreachProperty(obj, fn){
        if(!obj || !fn){return;}
        for (var name in obj) {
            if(!name){continue;}
            var isSelfProperty = Object.prototype.hasOwnProperty.call(obj, name);
            if (isSelfProperty) { // 自有属性,回调
                var value = obj[name];
                var result = fn(name, value, obj);
                if(false === result){break;}
            }
        }
    };
    //
    function getLoadTimes() {
        var loadTimes = {};
        // 调用 Chrome
        if (window["chrome"] && window["chrome"]["loadTimes"]) {
            loadTimes = chrome.loadTimes();
        } else {
            // 填充其他值?
        }
        //
        return loadTimes;
    };
    //
    function toRender(loadTimes){
        //
        foreachProperty(loadTimes, function(n, v){
            //
            (document.getElementById(n) || {}).innerText = v;
        });
        //
        var str = JSON.stringify(loadTimes);
        //
        str = str.replace("{", "{\n    ").replace("}", "\n}").replace(/,/g, "\n    ,");
        //
        document.querySelector("#resultJSON").innerText = str;

    };
    if (window["chrome"] && window["chrome"]["loadTimes"]) {
        window.setTimeout(function () {
            loadTimes = chrome.loadTimes();
            //
            toRender(loadTimes);
        }, 10);
    }

</script>
</body>
</html>
