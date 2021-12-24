package com.cncounter.util.net;

import com.alibaba.druid.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 网络工具
 */
public class URLUtils {

    public static String get(String url){
        String reply = null;
        //
        try {
            reply = executeGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        return reply;
    }

    public static String executeGet(String url) throws Exception {

        String content = null;
        CloseableHttpResponse response = null;
        try {
            // 定义HttpClient
            CloseableHttpClient client = HttpClients.createDefault();
            // 实例化HTTP方法
            HttpGet request = new HttpGet(url);
            response = client.execute(request);
            //
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity, "UTF-8");
        } finally {
            if (response != null) {
                try {
                    response.close();// 最后要关闭
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return content;
        }
    }

    /**
     * 组合两个URI；主要是处理2个斜线或者没有斜线 "/" 问题
     * @param basePath
     * @param subUri
     * @return
     */
    public static String concatUri(String basePath, String subUri){
        final  String SLASH = "/";
        //
        if(null == basePath){
            basePath = "";
        }
        if(null == subUri){
            subUri = "";
        }
        String resultUri = basePath;
        if(resultUri.endsWith(SLASH) && subUri.startsWith(SLASH)){
            resultUri = resultUri.substring(0, resultUri.length()-1);
        } else if(!resultUri.endsWith(SLASH) && !subUri.startsWith(SLASH)){
            resultUri += SLASH;
        }
        //
        resultUri += subUri;
        //
        return resultUri;
    }



    /**
     * 将query字符串解析为Map
     * @param query
     * @return
     */
    public static Map<String, String> parseQueryToMap(String query) {
        Map<String, String> queryParams = new LinkedHashMap<>();
        if (StringUtils.isEmpty(query)) {
            return queryParams;
        }
        // 去掉首尾空格
        query = query.trim();
        // 去掉?号及前面的部分
        int questionIndex = query.indexOf("?");
        if (questionIndex >= 0) {
            query = query.substring(questionIndex + 1);
        }
        // 去掉#号及后面的部分
        int poundIndex = query.indexOf("#");
        if (poundIndex >= 0) {
            query = query.substring(0, poundIndex);
        }
        if (StringUtils.isEmpty(query)) {
            return queryParams;
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            if (StringUtils.isEmpty(pair)) {
                continue;
            }
            String[] tokens = pair.split("=");
            if (null == tokens || tokens.length != 2) {
                continue;
            }
            String name = tokens[0];
            String value = tokens[1];

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(value)) {
                // do nothing
            } else {
                queryParams.put(name, value);
            }
        }
        return queryParams;
    }

    public static void main(String[] args) {
        String url = "https://grafana.cncounter.com/d/VTyZYgfGz/jvmhui-zong-zhi-biao?orgId=1&refresh=5s&from=now-5m&to=now";
        String url2 = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%23%3F%E6%B5%8B%E8%AF%95%3D%3D%3D&fenlei=256&rsv_pq=d77c339b00014511&rsv_t=e65cB6t22BV5KUR67K56yY21mPKoSk%2FKhQOOmxbxyVPfXGt1R4LIIIGpvCc&rqlang=cn&rsv_dl=tb&rsv_enter=1&rsv_sug3=10&rsv_sug1=7&rsv_sug7=101&rsv_sug2=0&rsv_btype=i&inputT=6497&rsv_sug4=7621#666";
        String url3 = "?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%23%3F%E6%B5%8B%E8%AF%95%3D%3D%3D&fenlei=256&rsv_pq=d77c339b00014511&rsv_t=e65cB6t22BV5KUR67K56yY21mPKoSk%2FKhQOOmxbxyVPfXGt1R4LIIIGpvCc&rqlang=cn&rsv_dl=tb&rsv_enter=1&rsv_sug3=10&rsv_sug1=7&rsv_sug7=101&rsv_sug2=0&rsv_btype=i&inputT=6497&rsv_sug4=7621#666";
        String url4 = "ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%23%3F%E6%B5%8B%E8%AF%95%3D%3D%3D&fenlei=256&rsv_pq=d77c339b00014511&rsv_t=e65cB6t22BV5KUR67K56yY21mPKoSk%2FKhQOOmxbxyVPfXGt1R4LIIIGpvCc&rqlang=cn&rsv_dl=tb&rsv_enter=1&rsv_sug3=10&rsv_sug1=7&rsv_sug7=101&rsv_sug2=0&rsv_btype=i&inputT=6497&rsv_sug4=7621#666";

        //

        //
        System.out.println(parseQueryToMap(url));
        System.out.println(parseQueryToMap(url2));
        System.out.println(parseQueryToMap(url3));
        System.out.println(parseQueryToMap(url4));
    }
}
