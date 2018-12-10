<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cncounter.cncounter.config.WebSiteConfig" %>
<%@include file="basePath.jsp"%>
	<meta charset="utf-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<!-- 对于国内双核浏览器强制使用Webkit内核渲染页面 -->
	<!-- 360 6.X 以上可识别 -->
	<meta name="renderer" content="webkit">
	<!-- 其他双核可识别-->
	<meta name="force-rendering" content="webkit">
	<!-- 对于没有自带 IE7 内核的浏览器 强制使用用户已安装的最高版本浏览器渲染, 有Chrome框架的优先使用-->
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
	<!-- 手机浏览器自适应宽度 -->
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link href="<%=basePath %>static/image/favicon.ico" rel="bookmark" type="image/x-icon" /> 
	<link href="<%=basePath %>static/image/favicon.ico" rel="icon" type="image/x-icon" /> 
	<link href="<%=basePath %>static/image/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<%
	String jqv = "1.9.1"; // jQuery版本
	String bsv = "3.3.4"; // BootStrap版本
	// 根据参数设置,决定是从本机还是从CDN获取CSS,JS资源
    // 根据参数设置,决定是从本机还是从CDN获取CSS,JS资源
    boolean debugmode = WebSiteConfig.isDEBUG_MODE();
	if(debugmode || true){
%>
	<script src="<%=basePath %>static/jquery/<%=jqv%>/jquery.min.js"></script>
	<script src="<%=basePath %>static/bootstrap/<%=bsv%>/js/bootstrap.min.js"></script>
	<link  href="<%=basePath %>static/bootstrap/<%=bsv%>/css/bootstrap.min.css" rel="stylesheet">
	<link  href="<%=basePath %>static/bootstrap/<%=bsv%>/css/bootstrap-theme.min.css" rel="stylesheet">
<%
	} else {
	//
%>
	<!-- 引入 jQuery -->
	<script src="//cdn.bootcss.com/jquery/<%=jqv%>/jquery.min.js"></script>
	<!-- BootStrap JS -->
	<script src="//cdn.bootcss.com/bootstrap/<%=bsv%>/js/bootstrap.min.js"></script>
	<!-- BootStrap -->
	<link href="//cdn.bootcss.com/bootstrap/<%=bsv%>/css/bootstrap.min.css" rel="stylesheet">
	<!-- BootStrap theme -->
	<link href="//cdn.bootcss.com/bootstrap/<%=bsv%>/css/bootstrap-theme.min.css" rel="stylesheet">
<%
	}
%>

	<script src="<%=basePath %>static/layer/layer.js?v=1"></script>
	<!--[if !IE]><!-->
	<script src="<%=basePath %>static/js/clipboard.js?v=1"></script>
	<!--<![endif]-->
	<!-- 本站的JS工具类 -->
	<script src="<%=basePath %>static/js/cncounter-util.js?v=1"></script>
	<script src="<%=basePath %>static/js/getLanIP.js?v=1"></script>
	<!-- 本站CSS样式 -->
	<link href="<%=path%>/static/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path%>/static/css/sticky-footer.css" rel="stylesheet" type="text/css" />