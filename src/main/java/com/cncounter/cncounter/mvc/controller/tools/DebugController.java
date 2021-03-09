package com.cncounter.cncounter.mvc.controller.tools;


import com.cncounter.cncounter.mvc.msg.JSONMessage;
import com.cncounter.util.common.JVMUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/debug")
public class DebugController {

    // @ApiOperation(value = "snapThreadDump", nickname = "系统线程状态", notes = "系统线程状态")
    @RequestMapping({"/thread_dump"})
    public JSONMessage snapThreadDump(HttpServletRequest request) {
        //
        String threadDump = JVMUtils.snapThreadDump();
        //
        JSONMessage result = JSONMessage.successMessage();
        result.addMeta("threadDump", threadDump);
        //
        return result;
    }

    // @ApiOperation(value = "histogram", nickname = "获取JVM对象统计信息", notes = "获取JVM对象统计信息")
    @RequestMapping({"/histogram"})
    public JSONMessage histogram(HttpServletRequest request) {
        //
        String histogram = JVMUtils.histogram(500);
        //
        JSONMessage result = JSONMessage.successMessage();
        result.addMeta("histogram", histogram);
        //
        return result;
    }
}
