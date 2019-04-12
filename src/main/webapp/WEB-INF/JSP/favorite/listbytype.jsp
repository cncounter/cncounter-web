<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.cncounter.cncounter.model.other.Favorite"%>
<%@page import="java.util.*"%>
<%@page import="com.cncounter.common.util.StringNumberUtil"%>

<%@include file="../basePath.jsp"%>
<%
	Object _favorites = request.getAttribute("favorites");
	Object _type = request.getAttribute("type");
	Object _requestURL = request.getAttribute("requestURL");
	//
	List<Favorite> favorites = new ArrayList<Favorite>();
	if(_favorites instanceof List<?>){
		favorites = (List<Favorite>)_favorites;
	}
	//
	Integer type = 0;
	if(_type instanceof Integer){
		type = (Integer)_type;
	}
	
	String requestURL = StringNumberUtil.toString(_requestURL);
	if(StringNumberUtil.isEmpty(requestURL)){
		requestURL = basePath;
	}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>收藏夹_<%=type %> - 性能计数器-cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="content_left">
			<p class="h1">收藏夹_<%=type %></p>
			
			<div>
					<%
					for(Favorite favorite : favorites){
						//
						String url = favorite.getUrl();
						String title = favorite.getTitle();
						//
						if(null == url){
							url = "";
						}
						if(url.startsWith("http:") || url.startsWith("https:")){
							
						} else {
                            final  String SLASH = "/";
                            String resultUri = basePath;
                            if(resultUri.endsWith(SLASH) && url.startsWith(SLASH)){
                                resultUri = resultUri.substring(0, resultUri.length()-1);
                            }
                            url = resultUri + url;
						}
						url = url.trim();
						
						//
						if(null == title || title.trim().isEmpty()){
							title = url;
						}
					%>
						<h2>
							<a target="_blank"  href="<%=url %>"><%=title %></a>
						</h2>
					<%
					}
					%>
			
			</div>
			
			
			<hr class="hr" style="border-top-color :black; width: 100%;"/>
			<p class="h2">添加收藏</p>
			<div>
				<form id="input_form" action="<%=basePath %>favorite/<%=type.intValue() %>/add.json" method="post">
					标题: <input tabindex="1" id="title" name="title" value="" >
					<br/>
					网址: <input tabindex="2" id="url" name="url" value="" >
					<br/>
					<br/>
				</form>
					<button tabindex="4" id="btn_commit"
						 type="button" class="btn btn-primary">添加</button>
			</div>
		</div>
		<jsp:include page="/common/sidebar.jsp"></jsp:include>
	</div>
	<!-- 多说评论框 start -->
	<div class="ds-thread" data-thread-key="<%=type.intValue() %>" data-title="收藏夹_<%=type.intValue() %>" data-url="<%=requestURL %>"></div>
	<!-- 多说评论框 end -->
	<!-- 多说公共JS代码 start (一个网页只需插入一次) -->
	<script type="text/javascript">
	var duoshuoQuery = {short_name:"cncounter"};
	(function() {
		var ds = document.createElement('script');
		ds.type = 'text/javascript';ds.async = true;
		ds.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//static.duoshuo.com/embed.js';
		ds.charset = 'UTF-8';
		(document.getElementsByTagName('head')[0] 
		 || document.getElementsByTagName('body')[0]).appendChild(ds);
	})();
	</script>
	<!-- 多说公共JS代码 end -->
	<jsp:include page="/common/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		// 此处JS应该归拢收集
		$(function(){
			//
			var $btn_commit = $("#btn_commit");
			var $input_form = $("#input_form");
			var $title = $("#title");
			var $url = $("#url");
			//
			//
			$btn_commit.bind("click", function(e){
				//
				var title = $title.val();
				var url = $url.val();
				if(!title){
					alert("标题不能为空!");
					return;
				}
				if(!url){
					alert("网址不能为空!");
					return;
				}
				//
				var action = $input_form.attr("action");
				var data = {
					title : title
					,
					url : url
				};
				
				var successCallback = function (message) {
		        	   var meta = message["meta"] || "";
		        	   var info = message["info"] || "";
		        	   var status = message["status"] || "";
		        	   //
		        	   confirm(info);
		        	   if(status){
		        		   refreshPage();
		        	   }
				    };
				//
				var errorCallback = function (jqXHR, textStatus, errorThrown) {
				    	// 把错误吃了
				        alert("网络请求失败");
				    };
				//
				postAjax(action, data, successCallback,errorCallback,1);
				//
			});
		});
	</script>
</body>
</html>