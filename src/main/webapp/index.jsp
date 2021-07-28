<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="common/basePath.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>性能计数器 - CNCounter</title>
    <meta property="qc:admins" content="515131327763637564526375" />
	<jsp:include page="/common/cssjs.jsp"></jsp:include>
	<script>
		;(function(){
			var CNC_HOSTNAME = "www.cncounter.com";
			toHttps(CNC_HOSTNAME);
		})();
	</script>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="content_left">
			<p class="h1">性能计数器</p>
			<h2>
				<a target="_blank" href="https://github.com/cncounter/cncounter-web">GitHub项目首页</a>
			</h2>
            <h2>
                <a target="_blank" href="https://github.com/cncounter/translation">CNCounter翻译文章</a>
            </h2>
            <h2>
                <a target="_self" href="./qrcode/input.php">工具-二维码生成</a>
            </h2>
			<h2>
				<a target="_self" href="./favorite/list/0.php">工具-简单收藏夹</a>
			</h2>
			<jsp:include page="/common/list999.jsp"></jsp:include>
		</div>
		<jsp:include page="/common/sidebar.jsp"></jsp:include>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
</body>
</html>