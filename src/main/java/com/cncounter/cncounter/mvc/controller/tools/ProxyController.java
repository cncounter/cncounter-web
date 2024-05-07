package com.cncounter.cncounter.mvc.controller.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cncounter.cncounter.model.other.ProxyRequest;
import com.cncounter.cncounter.mvc.controller.base.ControllerBase;
import com.cncounter.util.net.URLUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域请求代理.
 */
@RequestMapping({"/tools/proxy"})
@Controller
public class ProxyController extends ControllerBase {


    @RequestMapping({"/http1.json"})
    @ResponseBody
    public Object http1Proxy(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //
        String requestBody = IOUtils.toString(request.getInputStream());
        JSONObject json = (JSONObject) JSONObject.parse(requestBody);
        ProxyRequest proxyRequest = json.toJavaObject(ProxyRequest.class);
        // 1. 代理 get 请求
        String url = proxyRequest.getUrl();
        String method = proxyRequest.getMethod();
        if (ProxyRequest.GET.equals(method)) {
            String reply = URLUtils.get(url);
            try {
                return JSON.parse(reply);
            } catch (Exception ignore) {
                return reply;
            }
        } else {
            //
            response.setStatus(405, "UnSupported");
            //
            JSONObject resp = new JSONObject();
            resp.put("error", "未实现");
            return resp;
        }
    }

}
