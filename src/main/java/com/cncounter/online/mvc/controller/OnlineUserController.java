package com.cncounter.online.mvc.controller;

import com.cncounter.cncounter.mvc.msg.JSONMessage;
import com.cncounter.online.entity.OnlineUser;
import com.cncounter.online.service.OnlineUserService;
import com.cncounter.online.vo.OnlineUserVO;
import com.cncounter.util.string.StringNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在线用户相关.
 */
@Controller
@RequestMapping("/api/user")
public class OnlineUserController {
    @Autowired
    private OnlineUserService onlineUserService;

    // 注册
    @RequestMapping(value = {"/signup.json", "/register.json"})
    @ResponseBody
    public Object signup(HttpServletRequest request, HttpServletResponse response) {
        //
        JSONMessage jsonMessage = JSONMessage.newMessage();
        // 需要转换的内容
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        String password = request.getParameter("password");

        if(StringNumberUtil.isEmpty(password)){
            // 出错
            jsonMessage.setFailure().setInfo("密码不能为空");
            return jsonMessage;
        }
        if(StringNumberUtil.isEmpty(username) && StringNumberUtil.isEmpty(email) && StringNumberUtil.isEmpty(mobile)){
            // 出错
            jsonMessage.setFailure().setInfo("用户名/手机号/邮箱不能为空");
            return jsonMessage;
        }
        //
        OnlineUserVO onlineUserVO = new OnlineUserVO();
        onlineUserVO.setUsername(username);
        onlineUserVO.setMobile(mobile);
        onlineUserVO.setEmail(email);
        onlineUserVO.setPassword(password);
        //
        OnlineUser entity = OnlineUserVO.toPO(onlineUserVO);
        //
        //
        try{
            int rows = onlineUserService.register(entity);
            //
            if(rows < 1){
                jsonMessage.setFailure();
                jsonMessage.setInfo(entity.getValidReason());
                return jsonMessage;
            }
            if(rows > 0){
                jsonMessage.setInfo("注册成功");
                jsonMessage.setSuccess();
            }
        } catch (Exception e){
            jsonMessage.setInfo("注册失败:"+e.getMessage());
            jsonMessage.setFailure();
        }
        //
        return jsonMessage;
    }

}
