package com.bilicute.spacetime.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @描述: 配置邮箱信息，重新注入
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 19:39
 **/

@Configuration
public class MailConfig {
    @Value("${TimeMail.username}")
    private String username;
    @Value("${TimeMail.password}")
    private String password;
    @Value("${TimeMail.host}")
    private String host;
    @Value("${TimeMail.post}")
    private int post;
    //bean命名
    @Bean(name = "javaMailSender")
    public JavaMailSenderImpl createMailSender() {
        //创建一个 JavaMailSenderImpl 的实例，用于发送邮件。
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        //设置邮件服务器的主机。
        mailSender.setHost(host);
        //设置邮件服务器的端口号为 465。
        mailSender.setPort(post);
        ///设置发件人的邮箱地址
        mailSender.setUsername(username);
        //设置发件人的邮箱密码
        mailSender.setPassword(password);
        //创建一个名为 props 的 Properties 对象，用于保存邮件发送的相关配置信息。
        Properties props = mailSender.getJavaMailProperties();
        //将邮件的传输协议设置为 SMTPS（即带有 SSL 加密的 SMTP 协议）。
        props.put("mail.transport.protocol", "smtps");
        //表示启用 SSL 加密功能，保障邮件内容的安全。
        props.put("mail.smtp.ssl.enable", "true");
        props.put("smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //编码
        props.put("default-encoding","UTF-8");
        //：将上面创建的 props 对象设置到 JavaMailSenderImpl 实例中，以便能够进行加密传输。
        mailSender.setJavaMailProperties(props);
        //返回 JavaMailSenderImpl 的实例，作为 Bean 对象。其他组件可以通过依赖注入来获取这个实例，进行邮件发送等操作。
        return mailSender;
    }

    public String getUsername(){
        return this.username;
    }
}
