<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common/basePath.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>超级大乐透 - 性能计数器-cncounter</title>
	<jsp:include page="/common/cssjs.jsp"></jsp:include>

	<script type="text/javascript" src="notMultiClick/notMultiClick.js"></script>
	<style>
	/*
	参考: https://deepinout.com/css/css-questions/504_css_how_to_use_css_to_surround_a_number_with_a_circle.html
	*/
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
	.logs_area{
	    font-size: 24px;
	}
	</style>
</head>
<body>
	<jsp:include page="/common/header.jsp"></jsp:include>
	<div class="container-fluid">
		<div class="list-group">
			<button id="btn_generate_random_dlt">超级大乐透-随机生成</button><br />
			<div id="logs_dlt" class="logs_area"></div>
		</div>
		<div class="list-group">
		    <br/>
		</div>
		<div class="list-group">
		    <a target="_blank" href="https://www.lottery.gov.cn/kj/kjlb.html?dlt">超级大乐透-历史开奖</a><br/>
		    <a target="_blank" href="./randomnum.jsp">生成随机数</a><br/>
		</div>
	</div>
	<jsp:include page="/common/footer.jsp"></jsp:include>
	
	<script type="text/javascript">
		// 此处JS应该归拢收集
		$(function(){
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
                        var randomNum = randomByBase(headBase);
                        //
                        while(headArray.includes(randomNum)){
                          randomNum = randomByBase(headBase);
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
                        var randomNum = randomByBase(tailBase);
                        //
                        while(tailArray.includes(randomNum)){
                          randomNum = randomByBase(tailBase);
                        }
                        tailArray.push(randomNum);
                    }
                    tailArray.sort(compareFn);
                })();
                //
				var log = "<br/>";
				log += "<span class='circle_20_num'>" + (dltTotal++) + "</span>" ;
				log += "<span style='color:blue;'>" + "" ;
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

            // 重写一个随机数算法
            function randomByBase(base){
                var luckyNum = 666;
                var arc = new Date().getTime() + luckyNum;
                var temp =Math.abs(Math.sin(arc)) * Math.random() * 10;
                result = Math.ceil(base * temp);
                result = Math.ceil(result);
                result = 1 + result % base;
                return result;
            };

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

	<script type="text/javascript">
        // 请求AJAX,工具方法
        function requestAjax(url, data, type, successCallback, errorCallback, isParseJSON, context){
            //
            context = context || window;
            type = type || "POST";
            // 执行AJAX请求
            $.ajax({
                url: url,
                data: data,
                type: type,
                success: function (message) {
                    if(isParseJSON){
                        message = parseJSON2Object(message);
                    }
                    if(successCallback){
                       successCallback.call(context, message);
                    }
                    return false;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    // 错误
                   if(errorCallback){
                       errorCallback.apply(context, arguments);
                   } else {
                       CNC.msg("操作失败!");
                   };
                }
            });
        };
        function getAjax(url, data, successCallback, errorCallback, isParseJSON, context){
        	isParseJSON = isParseJSON || false;
        	var type = "GET";
        	return requestAjax(url, data, type, successCallback, errorCallback, isParseJSON, context);
        };
	    // 获取大乐透的校本
	    function loadAllDltHistory(callbackFn){
	        //
	        var href = window.location.href + "";
	        if(href.indexOf("www.lottery.gov.cn") < 1){
	            window.open('https://www.lottery.gov.cn/kj/kjlb.html?dlt')
	            return;
	        }
	        //
	        var allHistoryDataList = [];
	        var lotteryDrawNumLoaded = {};
	        // 获取地址;
	        var urlPrefix = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=100&isVerify=1&pageNo=";
	        // 当前页码
	        var currentPageNo = 1;
	        var pageSize = 100;
	        // 加载下一页;
	        function loadNextPage(prefix, pageNum){
	            prefix = prefix || urlPrefix;
	            pageNum = pageNum || currentPageNo;
	            var url = prefix + pageNum;
                //
                getAjax(url, null, successCallback, errorCallback);
	        };
            // 获取成功;
            function successCallback(resp){
                resp = resp || {};
                if(resp.length){
                  resp = JSON.parse(resp);
                }
                var errorMessage = resp.errorMessage || "";
                console.info("Ajax获取成功; 当前进度: currentPageNo=" + currentPageNo + "; 返回消息: " + errorMessage);
                console.dir(resp);
                currentPageNo += 1;
                // 打印日志
                // console.dir(resp);
                //
                var value = resp.value || {};
                var list = value.list || [];
                //
                list.forEach(function(v, i){
                  var lotteryDrawNum = v.lotteryDrawNum;
                  // 已经加载到; // 忽略
                  if(lotteryDrawNumLoaded[lotteryDrawNum]){
                    return;
                  }
                  // 存起来
                  lotteryDrawNumLoaded[lotteryDrawNum] = 1;
                  allHistoryDataList.push(v);

                });
                // 判断结果
                if(list.length < pageSize){
                  window.allHistoryDataList_ = allHistoryDataList;
                  // 触发执行结束;
                  if(callbackFn){
                    console.info("执行结束; 获取到的: list.length=" + (list.length)+ "; 即将调用回调方法;");
                    callbackFn.apply(window, allHistoryDataList);
                  } else {
                    console.info("执行结束; 当前进度: dataSize=" + (allHistoryDataList.length)+ "; 数据信息已经赋值给 window.allHistoryDataList_ ");
                  }
                }
                var sleepSeconds = 10;
                console.info("数据解析完成; 当前进度: dataSize=" + (allHistoryDataList.length)+ "; 请等待" + sleepSeconds, "秒; 系统自动执行下一次请求");
                // 触发下一页加载; 闭包;
                window.setTimeout(function(){
                	    loadNextPage();
                	}, sleepSeconds * 1000);
            };
            function errorCallback(jqXHR, textStatus, errorThrown){
                console.warn("Ajax获取失败; 当前进度: currentPageNo=" + currentPageNo + "; dataSize=" + (allHistoryDataList.length)+ "; 请等待 60S 后自动重试;", jqXHR, textStatus, errorThrown);
                // 触发下一页加载; 闭包;
                window.setTimeout(function(){
                	    loadNextPage();
                	}, 60000);
            };
            // 触发执行;
            loadNextPage();
            //
            return allHistoryDataList;
	    };
	    // loadAllDltHistory()

	    function copyDemo(){
	      var list = window.allHistoryDataList_;
	      list.forEach(function(v, i){
	        // 去掉字段
            v.prizeLevelList = null;
          });
          var dataStr = JSON.stringify(list);
          // 这个 copy 不能在函数内部调用, 只能在控制台输入;
          copy(dataStr);
          console.info("执行结束; 整体数据已拷贝到剪贴板!!");}
	    }


	</script>
</body>
</html>