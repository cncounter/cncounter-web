<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="javax.servlet.http.HttpUtils,java.util.Enumeration" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page import="java.util.concurrent.atomic.AtomicInteger" %>
<%@ page import="com.cncounter.util.net.IPUtils" %>
<%@ page import="com.cncounter.util.string.StringNumberUtil" %>
<%@ page import="com.alibaba.fastjson.JSON" %>

<%!
    public static ConcurrentHashMap<String, AtomicInteger> visitCounterMap
            = new ConcurrentHashMap<String, AtomicInteger>();
%>

<%
    // 获取客户端IP地址
    String clientIp = IPUtils.getClientIp(request);
    Integer visitCount = 0;
    if(StringNumberUtil.notEmpty(clientIp)){
        //
        AtomicInteger visitCounter = visitCounterMap.get(clientIp);
        if(null == visitCounter){
            visitCounter = new AtomicInteger();
            visitCounterMap.putIfAbsent(clientIp, visitCounter);
        }
        if(null != visitCounter){
            visitCount = visitCounter.incrementAndGet();
        }
    }
%>

<%
    // 如果需要清空数据
    String action = request.getParameter("action");
    if("clear".equalsIgnoreCase(action)) {
        visitCounterMap.remove(clientIp);
        visitCount = 0;
    }
%>
<%
    // 如果需要返回JSON格式的数据
    String format = request.getParameter("format");
    if("json".equalsIgnoreCase(format)){
        // 返回JSON
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("clientIp", clientIp);
        result.put("visitCount", visitCount);
%>
<%=JSON.toJSONString(result)%>
<%
        return;
    }
%>
<HTML>
<HEAD>
    <TITLE>统计页面访问次数</TITLE>
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
<%

%>

<H1>统计页面访问次数</H1>
<table border="1" width="100%">

    <thead>
    <tr>
        <td align="center">
            <h3>IP地址</h3>
        </td>
        <td align="center">
            <h3>访问次数</h3>
        </td>
    </tr>
    </thead>
    <tbody>
<%
    Set<String> keySet = visitCounterMap.keySet();
    // 排序?
    // 根据值排序?
    for(String key: keySet){
%>
    <tr>
        <td>
            <%=key%>
        </td>
        <td>
            <%=visitCounterMap.getOrDefault(key, new AtomicInteger(0)).intValue()%>
        </td>
    </tr>
<%
    }
%>
    </tbody>
</table>

</BODY>
</HTML>

