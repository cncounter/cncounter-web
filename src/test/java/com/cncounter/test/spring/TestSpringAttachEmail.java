package com.cncounter.test.spring;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

public class TestSpringAttachEmail {
    public static void main(String[] args) throws Exception {
        // 发送带附件的MIME邮件
        sendAttachmentEmail();
    }

    // 发送带附件的MIME邮件
    public static void sendAttachmentEmail() throws MessagingException, IOException {
        // 邮件发送器
        JavaMailSender mailSender = getJavaMailSender();
        // MIME 邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 辅助类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        // 邮件信息
        helper.setFrom("10001@qq.com"); // 发件人邮箱
        helper.setTo("10086@vip.qq.com"); // 收件人邮箱
        helper.setSubject("测试Spring发送附件-1"); // 标题
        helper.setText("您的文件: PWA开发简介.zip; 请下载附件!"); // 文本信息

        // 增加1个附件; 可以使用多种资源API
        String fileName1 = "E:/PWA开发简介.zip";
        InputStream inputStream1 = new FileInputStream(fileName1);
        //
        // Java Mail 会打开2次 InputStreamResource
        // 第一次确定编码, 第二次才执行编码。
        ByteArrayResource byteResource1 =
                new ByteArrayResource(IOUtils.toByteArray(inputStream1));
        // 所以不能使用 InputStreamResource
        // InputStreamResource attachment1 = new InputStreamResource(inputStream1);
        helper.addAttachment("PWA开发简介.zip", byteResource1);
        try {
            mailSender.send(mimeMessage);
            System.out.println("邮件发送成功!"); // 没有报错就是好消息 :-)
        } catch (MailException ex) {
            System.err.println("发送失败:" + ex.getMessage());
        } finally {
            IOUtils.closeQuietly(inputStream1);
        }
    }

    // 获取邮件发送器
    public static JavaMailSender getJavaMailSender() {
        //
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 参考QQ邮箱帮助中心
        mailSender.setHost("smtp.qq.com"); // QQ邮箱smtp发送服务器地址
        //mailSender.setPort(465); // QQ这个端口不可用? 为什么?
        mailSender.setPort(587);// 端口号
        mailSender.setUsername("10001@qq.com"); // 使用你自己的账号
        mailSender.setPassword("usbusbcnzztbsbtob"); // 授权码-发短信获取
        //
        // 相关属性配置, 也可以不修改,使用默认值
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");// 协议
        props.put("mail.smtp.auth", "true");// 登录
        props.put("mail.smtp.starttls.enable", "true");//使用SSL
        props.put("mail.debug", "true");// 调试信息输出
        //
        return mailSender;
    }
}