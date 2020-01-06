<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/basePath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>MD5值计算 - 性能计数器-cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
        <div class="form-group">
            <label for="plainText">
                明文文本<span class="required">:</span>
            </label>
            <br/>
            <textarea tabindex="1" id="plainText" name="plainText" rows="4" cols="40"></textarea>
        </div>
		<div class="list-group">

            <button tabindex="2" id="btn_generate_md5"
                    type="button" class="btn btn-primary">MD5值计算</button>
			<br />
			<div id="result">
                MD5值: <input tabindex="3" id="md5_result" name="md5_result" size="36" >
            </div>
            <button tabindex="4" id="btn_to_uppercase"
                    type="button" class="btn btn-primary">转换为大写</button>
            <br />
            <br />
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		// 此处JS应该归拢收集
		$(function(){

			var $btn_generate_md5 = $("#btn_generate_md5");
			var $btn_to_uppercase = $("#btn_to_uppercase");
			var $plainText = $("#plainText");
			var $md5_result = $("#md5_result");
			//
			initBindEvents();
			//
			function initBindEvents(){
				//
                $btn_generate_md5.bind("click", md5_click_handler);
                $btn_to_uppercase.bind("click", md5_to_uppercase_handler);
				// 加载md5工具.js
				loadMd5Utils();
			};
			//
			function md5_click_handler(){
                //
                var plainText = $plainText.val();
                var md5Hash = md5(plainText);
                //
                $md5_result.val(md5Hash);
			};
            //
            function md5_to_uppercase_handler(){
                //
                var md5Hash = $md5_result.val() || "";
                //
                $md5_result.val(md5Hash.toUpperCase());
            };

		});
	</script>
</body>
</html>