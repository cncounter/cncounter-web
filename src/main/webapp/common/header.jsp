<%@ page import="com.cncounter.cncounter.model.view.UserVO" %>
<%@ page import="com.cncounter.cncounter.mvc.controller.base.ControllerBase" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="basePath.jsp"%>
  <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container-fluid">
        <div class="navbar-header">
        <%--
        这个按钮是在手机上显示的。
         --%>
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">显示</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="http:<%=basePath %>">性能计数器</a>
        </div>
        
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav navbar-right">
              <%--<jsp:include page="bshare.jsp"/>--%>
            <%
                UserVO userVO = ControllerBase.getLoginUser(request);
                if(null == userVO){
            %>
              <li><a href="https:<%=basePath %>login.php">登录</a></li>
            <%
                } else {
            %>
              <%--
                  如果是已登录用户,则应该切换为显示用户昵称,以及退出按钮
               --%>
              <li><a href="<%=path %>/userInfo.php"><%=userVO.getNickName()%></a></li>
              <li><a href="<%=path %>/logout.php">退出</a></li>
            <%
                }
            %>
          </ul>
        </div>
      </div>
  </nav>
  <%-- 这里垫一个DIV会不会比较好 --%>
<div>

</div>