package com.cncounter.cncounter.model.other;


import java.util.Map;

public class ProxyRequest {
    public static final String POST = "post";
    public static final String GET = "get";
    private String url;
    private String method;
    private String body;
    private Map<String, String> headers;
    private String proxy = "https://cncounter.com/tools/proxy/http1.json";

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

}
