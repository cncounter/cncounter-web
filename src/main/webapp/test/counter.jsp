<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="java.util.concurrent.ConcurrentHashMap" %>
<%@ page import="java.util.concurrent.atomic.AtomicInteger" %>
<%@ page import="com.cncounter.util.net.IPUtils" %>
<%@ page import="com.alibaba.fastjson.JSON" %>
<%!
    // 访问计数器Map<IP地址, 次数>
    private static ConcurrentHashMap<String, AtomicInteger> visitCounterMap
            = new ConcurrentHashMap<String, AtomicInteger>();

    // 增加并获取最新的访问次数
    private static int incrementCounter(String clientIp) {
        //
        AtomicInteger visitCounter = visitCounterMap.get(clientIp);
        if (null == visitCounter) {
            visitCounter = new AtomicInteger();
            AtomicInteger oldValue = visitCounterMap.putIfAbsent(clientIp, visitCounter);
            if (null != oldValue) {
                // 使用 putIfAbsent 时注意: 判断是否有并发导致的原有值。
                visitCounter = oldValue;
            }
        }
        // 先增加, 再返回
        int count = visitCounter.incrementAndGet();
        return count;
    }

    // 清除某个IP的访问次数
    private static int clearCounter(String clientIp) {
        visitCounterMap.remove(clientIp);
        return 0;
    }

    //
    private static final String CONST_PARAM_NAME_ACTION = "action";
    private static final String CONST_ACTION_VALUE_CLEAR = "clear";
    //
    private static final String CONST_PARAM_NAME_FORMAT = "format";
    private static final String CONST_FORMAT_VALUE_JSON = "json";
    //
    private static final String CONST_ATTR_NAME_CLIENTIP = "clientIp";
    private static final String CONST_ATTR_NAME_VISITCOUNT = "visitCount";
%>
<%
    // 获取客户端IP地址
    String clientIp = IPUtils.getClientIp(request);
    Integer visitCount = 0;
    if (null != clientIp) {
        // 获取访问次数
        visitCount = incrementCounter(clientIp);
    }
    // 如果需要清空数据
    String action = request.getParameter(CONST_PARAM_NAME_ACTION);
    if (CONST_ACTION_VALUE_CLEAR.equalsIgnoreCase(action)) {
        visitCount = clearCounter(clientIp);
    }
    // 如果需要返回JSON格式的数据
    String format = request.getParameter(CONST_PARAM_NAME_FORMAT);
    if (CONST_FORMAT_VALUE_JSON.equalsIgnoreCase(format)) {
        // 返回JSON
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(CONST_ATTR_NAME_CLIENTIP, clientIp);
        result.put(CONST_ATTR_NAME_VISITCOUNT, visitCount);
%>
<%=JSON.toJSONString(result)%>
<%
        return; // 如果返回JSON数据, 则不往下执行
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
        for (String key : keySet) {
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

