<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/basePath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>随机数使用 - 性能计数器-cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>

	<script type="text/javascript" src="notMultiClick/notMultiClick.js"></script>
	<style>
	.circle_20_num {
	    display: flex;
        justify-content: center;
        align-items: center;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        background-color: #f5f5f5;
        border: 1px solid #ccc;
        font-family: Arial;
        font-size: 16px;
        color: #333;
	}
	</style>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="list-group">
			<button id="btn_generate_random">生成随机数</button><br />
			<div id="logs"></div>
		</div>
		<div class="list-group">
			<button id="btn_generate_random_dlt">大乐透-生成</button><br />
			<div id="logs_dlt"></div>
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
			//
			var dltTotal = 1;
			//
			notMultiClick($("#btn_generate_random_dlt"), function(){
                //
                // 前区=5个; [1, 35];
                var headArray = [];
                (function(){
                    var headNum = 5;
                    var headBase = 35;
                    for(var i=0; i<headNum; i++){
                        var randomNum = Math.ceil(headBase * Math.random());
                        //
                        while(headArray.includes(randomNum)){
                          randomNum = randomNum = Math.ceil(headBase * Math.random());
                        }
                        headArray.push(randomNum);
                    }
                    headArray.sort(compareFn);
                })();
                var tailArray = [];
                (function(){
                    var tailNum = 2;
                    var tailBase = 12;
                    for(var i=0; i<tailNum; i++){
                        var randomNum = Math.ceil(tailBase * Math.random());
                        //
                        while(tailArray.includes(randomNum)){
                          randomNum = randomNum = Math.ceil(tailBase * Math.random());
                        }
                        tailArray.push(randomNum);
                    }
                    tailArray.sort(compareFn);
                })();
                //
				var log = "<br/>";
				log += "<span class='circle_20_num'>" + (dltTotal++) + "</span>" ;
				log += "<span style='color:blue;'>" + "1: " ;
				headArray.forEach(function(v, i){
				        log += format2Digest(v) + "  ";
				    });
				log += "</span>";
				log += "<span> + </span>";
				log += "<span style='color:red;'>";
				tailArray.forEach(function(v, i){
				        log += format2Digest(v) + "  ";
				    });
				log += "</span>";
				//
				var currLog = $("#logs_dlt").html() || "";
				$("#logs_dlt").html(currLog + log);
			});


            // 数字比较
			function compareFn(num1, num2){
			  return num1 - num2;
			};

            // 转变成2位数
			function format2Digest(num){
			    var result = "" + num;
			    if(num < 10){
			        result = "0" + result;
			    }
			    return result;
			}

		});
	</script>
</body>
</html>