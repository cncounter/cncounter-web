<%@page import="com.cncounter.util.spring.SpringContextHolder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>欢迎您 - cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="content_left">
			<p class="h1">性能计数器</p>
			<h1> SpringContextHolder</h1>
			<h2>
				SpringContextHolder.getApplicationContext().getClass().getName(): <%=SpringContextHolder.getApplicationContext().getClass().getName() %>
			</h2>
			<h2>
				<a target="_blank" href="./druid/weburi.html">Druid监控页面</a>
			</h2>
		</div>
		<jsp:include page="/common/sidebar.jsp"></jsp:include>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>