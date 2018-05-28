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

<hr/>

<h2>实现代码</h2>

<textarea rows="14" cols="60" readonly="readonly" style="background-color: #eeeeee;">
public class TestJInfo {
    public static void main(String[] args) {
        // 获取所有系统属性
        Properties properties = System.getProperties();
        // 遍历
        Set keySet= properties.keySet();
        for(Object key : keySet){
            Object value = properties.get(key);
            System.out.println(key+"="+value);
        }
    }
}
</textarea>

<hr/>

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
        //
        Set<String> secretyKeys = new HashSet<>();
        secretyKeys.add("com.sun.management.jmxremote.port");
        secretyKeys.add("java.rmi.server.hostname");
        //
        Properties properties = System.getProperties();
        //
        Set<Object> keySet = properties.keySet();
        for (Object key : keySet) {
            Object value = properties.get(key);

            if(secretyKeys.contains(key)){
                value = "******保密*****";
            }
            if(value != null){
                String v = value.toString();
                v = v.replace("\r", "\\r");
                v = v.replace("\n", "\\n");
                value = v;
            }
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

