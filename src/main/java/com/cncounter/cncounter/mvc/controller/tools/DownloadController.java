package com.cncounter.cncounter.mvc.controller.tools;

import com.cncounter.cncounter.mvc.controller.base.ControllerBase;
import com.cncounter.cncounter.mvc.msg.JSONMessage;
import com.cncounter.util.common.Config;
import com.cncounter.util.net.HttpClientUtils;
import com.cncounter.util.string.StringNumberUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 跨域AJAX代理.
 */
@RequestMapping({"/download"})
@Controller
public class DownloadController extends ControllerBase {

    //
    final String downloadUrlPrefix = Config.sysconfig.get("sys.download.downloadUrlPrefix");
    final String targetDirectoryPath = Config.sysconfig.get("sys.download.targetDirectoryPath");

    @RequestMapping({"/genfileurl.json"})
    @ResponseBody
    public Object genfileurl(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        try {
            return _genFileUrl(request, response);
        } catch (Throwable t) {
            return JSONMessage.failureMessage().setInfo("处理失败");
        }
    }

    @RequestMapping({"/existsfile.json"})
    @ResponseBody
    public Object existsfile(HttpServletRequest request, HttpServletResponse response) throws MalformedURLException {
        // 1.1. 暂时不处理 cookies, 以及headers。
        Map<String, String> paramMap = parseParamMap(request);
        //
        String origfileurl = paramMap.get("origfileurl");
        String targetfilename = paramMap.get("targetfilename");
        //
        //
        if (StringNumberUtil.isEmpty(targetfilename)) {
            if (StringNumberUtil.notEmpty(origfileurl)) {
                URL url = new URL(origfileurl);
                String path = url.getPath();
                if (null == path || path.trim().isEmpty()) {
                    return JSONMessage.failureMessage().setInfo("只支持 http 协议");
                }
                targetfilename = path.substring(path.lastIndexOf("/")+1);
            }
        }
        if (null != targetfilename && targetfilename.contains("/")) {
            targetfilename = targetfilename.substring(targetfilename.lastIndexOf("/")+1);
        }
        //
        if (targetfilename.endsWith(".jsp")) {
            // 暂时不管
            //return JSONMessage.failureMessage().setInfo("不支持 jsp 文件下载");
        }
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            return JSONMessage.failureMessage().setInfo("不存在");
        }
        //
        final File targetFile = new File(targetDirectory, targetfilename);
        //
        if (targetFile.exists()) {
            // 返回
            return JSONMessage.successMessage().setInfo("文件已存在")
                    .addMeta("targetfilename", targetfilename)
                    .addMeta("downloadurl", downloadUrlPrefix + targetfilename)
                    ;
        }
        //
        return JSONMessage.failureMessage().addMeta("targetFile", targetFile.getAbsolutePath()).setInfo("文件不存在");
    }

    public Object _genFileUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1.1. 暂时不处理 cookies, 以及headers。
        Map<String, String> paramMap = parseParamMap(request);
        //
        String origfileurl = paramMap.get("origfileurl");
        logger.debug("origfileurl: " + origfileurl);
        if (StringNumberUtil.isEmpty(origfileurl)) {
            return JSONMessage.failureMessage().setInfo("origfileurl为空");
        } else if (false == origfileurl.startsWith("http")) {
            return JSONMessage.failureMessage().setInfo("只支持 http 协议");
        }
        //
        URL url = new URL(origfileurl);
        //
        String targetfilename = paramMap.get("targetfilename");
        if (StringNumberUtil.isEmpty(targetfilename)) {
            String path = url.getPath();
            if (null == path) {
                return JSONMessage.failureMessage().setInfo("只支持 http 协议");
            }
            targetfilename = path.substring(path.lastIndexOf("/"));
        }
        if (null != targetfilename && targetfilename.contains("/")) {
            targetfilename = targetfilename.substring(targetfilename.lastIndexOf("/")+1);
        }
        //
        if (targetfilename.endsWith(".jsp")) {
            // 暂时不管
            //return JSONMessage.failureMessage().setInfo("不支持 jsp 文件下载");
        }
        if (targetfilename.trim().isEmpty()) {
            // 暂时不管
            return JSONMessage.failureMessage().setInfo("不支持目录下载");
        }
        File targetDirectory = new File(targetDirectoryPath);
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        //
        final File targetFile = new File(targetDirectory, targetfilename);
        //
        if (targetFile.exists()) {
            // 直接返回
            return JSONMessage.successMessage().setInfo("文件已存在")
                    .addMeta("targetfilename", targetfilename)
                    .addMeta("downloadurl", downloadUrlPrefix + targetfilename)
                    ;
        }
        //
        final InputStream inputStream = HttpClientUtils.getUrlAsStream(url);
        if (null == inputStream) {
            return JSONMessage.failureMessage().setInfo("操作失败");
        }

        // 下载文件操作。 应该丢给线程池
        HttpClientUtils.downloadThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                //
                String targetFullPath = targetFile.getAbsolutePath();
                //
                String TMP = ".tmp";
                String tempFullPath = targetFullPath + TMP;
                //
                FileOutputStream outputStream = null;
                try {
                    // 需要下载到temp,再 rename
                    //
                    outputStream = new FileOutputStream(tempFullPath);
                    IOUtils.copy(inputStream, outputStream);
                    //
                } catch (Throwable e) {
                    logger.error("下载文件失败.", e);
                } finally {
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                }
                // 关掉 outputStream 之后才能重命名- Linux 不报错可以改名称, Windows不行。
                File tempFile = new File(tempFullPath);
                if (tempFile.exists()) {
                    tempFile.renameTo(targetFile);
                }
            }
        });
        //
        return JSONMessage.successMessage().setInfo("文件正在下载")
                .addMeta("downloadurl", downloadUrlPrefix + targetfilename);
    }

}
