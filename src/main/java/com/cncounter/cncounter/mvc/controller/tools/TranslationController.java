package com.cncounter.cncounter.mvc.controller.tools;

import com.cncounter.common.web.ControllerBase;
import com.cncounter.common.vo.JSONMessage;
import com.cncounter.cncounter.service.api.other.YoudaoFanyiService;
import com.cncounter.common.util.StringNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/tools/translation"})
public class TranslationController extends ControllerBase {

    @Autowired
    private YoudaoFanyiService youdaoFanyiService;
    /**
     * 翻译
     * @param request
     * @param response
     * @return
     */
    @RequestMapping({"/english.json"})
    @ResponseBody
    public Object jsonproxy(HttpServletRequest request, HttpServletResponse response) {
        //
        String text_original = request.getParameter("text_original");
        if(StringNumberUtil.isEmpty(text_original)){
            return JSONMessage.failure();
        }
        //
        String text_translation = youdaoFanyiService.translationToCN(text_original);
        //
        JSONMessage jsonMessage = JSONMessage.failure();
        jsonMessage.asSuccess();
        jsonMessage.addMeta("text_translation", text_translation);
        jsonMessage.addMeta("text_original",text_original);
        //
        return jsonMessage;
    }
}
