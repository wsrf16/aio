package com.aio.portable.swiss.suite.net.mail;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * MailUtils
 * refer:
 *   org.springframework.boot.autoconfigure.mail.MailProperties.class
 *   MailSenderPropertiesConfiguration.class
 *   MailSenderAutoConfiguration.class
 *   JavaMailSenderImpl.class
 *   JavaMailSender.class
 */
@Component
@ConditionalOnClass(JavaMailSender.class)
@ConditionalOnBean(JavaMailSender.class)
public class MailTo {
    private JavaMailSender javaMailSender;// = new JavaMailSenderImpl();

    public MailTo(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * spring:
     *   mail:
     *     host: smtp.qq.com #发送邮件服务器
     *     username: xx@qq.com #QQ邮箱
     *     password: xxxxxxxxxxx #客户端授权码
     *     protocol: smtp #发送邮件协议
     *     properties.mail.smtp.auth: true
     *     properties.mail.smtp.port: 465 #端口号465或587
     *     properties.mail.display.sendmail: Javen #可以任意
     *     properties.mail.display.sendname: Spring Boot Guide Email #可以任意
     *     properties.mail.smtp.starttls.enable: true
     *     properties.mail.smtp.starttls.required: true
     *     properties.mail.smtp.ssl.enable: true
     *     default-encoding: utf-8
     *     from: xx@qq.com #与上面的username保持一致
     *
     *
     * spring:
     *   mail:
     *     host: smtp.126.com
     *     username: xx@126.com
     *     password: xxxxxxxx
     *     protocol: smtp
     *     properties.mail.smtp.auth: true
     *     properties.mail.smtp.port: 994 #465或者994
     *     properties.mail.display.sendmail: Javen
     *     properties.mail.display.sendname: Spring Boot Guide Email
     *     properties.mail.smtp.starttls.enable: true
     *     properties.mail.smtp.starttls.required: true
     *     properties.mail.smtp.ssl.enable: true
     *     default-encoding: utf-8
     *     from: xx@126.com
     *
     *
     * spring:
     *   mail:
     *     host: smtp.gmail.com
     *     username:xxx@gmail.com
     *     password: xxxxx
     *     protocol: smtp
     *     properties.mail.smtp.auth: true
     *     properties.mail.smtp.port: 465
     *     properties.mail.display.sendmail: Javen
     *     properties.mail.display.sendname: Spring Boot Guide Email
     *     properties.mail.smtp.starttls.enable: true
     *     properties.mail.smtp.starttls.required: true
     *     properties.mail.smtp.ssl.enable: true
     *     default-encoding: utf-8
     *     from: xxx@gmail.com
     */

    /**
     * 发送文本邮件
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param cc
     */
    public void sendSimpleMail(String from, String to, String subject, String content, String... cc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setCc(cc);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    /**
     * 发送HTML邮件
     * @param from
     * @param to
     * @param subject
     * @param content
     * @throws MessagingException
     */
    public void sendHtmlMail(String from, String to, String subject, String content, String... cc) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setCc(cc);
        helper.setSubject(subject);
        helper.setText(content, true);

        javaMailSender.send(message);
    }

    /**
     * 发送附带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String from, String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);

        javaMailSender.send(message);
    }

    /**
     * 发送附带静态资源的邮件
     * @param from
     * @param to
     * @param subject
     * @param html
     * @param rscPath
     * @param rscId
     * @throws MessagingException
     */
    public void sendResourceMail(String from, String to, String subject, String html, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        javaMailSender.send(message);
    }

    /**
     * send
     * @param message
     */
    public void send(MimeMessage message) {
        javaMailSender.send(message);
    }

    /**
     * send
     * @param message
     */
    public void send(MimeMessage... message) {
        javaMailSender.send(message);
    }

    /**
     * send
     * @param message
     */
    public void send(SimpleMailMessage message) {
        javaMailSender.send(message);
    }

    /**
     * send
     * @param message
     */
    public void send(SimpleMailMessage... message) {
        javaMailSender.send(message);
    }

}
