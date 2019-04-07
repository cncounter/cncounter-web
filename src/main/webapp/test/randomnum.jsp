<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/basePath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>随机数使用 - 性能计数器-cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>

	<script type="text/javascript" src="notMultiClick.js"></script>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="list-group">
			<button id="btn_generate_random">生成随机数</button><br />
			<div id="logs"></div>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		// 此处JS应该归拢收集
		$(function(){
			//
			notMultiClick($("#btn_generate_random"), function(){
                //
                var randomnum = 10 * Math.random();
                //
				var log = "<br/>" + "<span>" + "randomnum:" + randomnum + "</span>";
				var currLog = $("#logs").html() || "";
				$("#logs").html(currLog + log);
			});

		});
	</script>
</body>
</html>