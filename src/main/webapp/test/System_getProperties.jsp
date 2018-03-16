<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page trimDirectiveWhitespaces="true" %>
<HTML>
<HEAD>
    <TITLE>System.getProperties信息监测页面</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <style>
        body {
            word-wrap: break-word;
            word-break: break-all;
        }
    </style>
</HEAD>
<BODY>

<H1>System.getProperties 监测页面</H1>

<h2>JVM 系统属性</h2>


<table border="1px" width="100%">

    <tbody>
    <tr>
        <td align="center">
            <h3>Properties_Name</h3>
        </td>
        <td align="center">
            <h3>Properties_VALUE</h3>
        </td>
    </tr>

    <%
        Properties properties = System.getProperties();
        //
        Set<Object> keySet = properties.keySet();
        for (Object key : keySet) {
            Object value = properties.get(key);
    %>
    <tr>
        <td width="200">
            <%=key%>
        </td>
        <td>
            <%=value%>
        </td>
    </tr>
    <%
        }
    %>

    </tbody>
</table>

</BODY>
</HTML>

