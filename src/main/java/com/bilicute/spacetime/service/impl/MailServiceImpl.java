package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.service.MailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @项目: everlast
 * @描述: 发送邮箱的实现方法类
 * @作者: i囡漫笔
 * @创建时间: 2023-12-02 21:14
 **/

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${TimeMail.username}")
    private String adminAddress;

    public void sendEmail(String toEmailAddress,String code,String userName){
        //使用 JavaMailSender 创建一个 MIME 类型的信息对象实例。
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //获取当前的时间日期信息。
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(date);
        String mailText = String.format("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title></title><style type=\"text/css\">@font-face{font-family:'OS_Sans';src:url('https://img1.i-nmb.cn/everlast.love/OS_Sans.woff')format('woff')}</style></head><body oncontextmenu=\"return false;\"ondragstart=\"return false;\"><div><includetail><div align=\"center\"><div class=\"open_email\"style=\"margin-left: 8px; margin-top: 8px; margin-bottom: 8px; margin-right: 8px;\"><div><br><span class=\"genEmailContent\"><div id=\"cTMail-Wrap\"style=\"word-break: break-all;box-sizing:border-box;text-align:center;min-width:320px; max-width:660px; border:1px solid #f6f6f6; background-color:#f7f8fa; margin:auto; padding:20px 0 30px; font-family:'helvetica neue',PingFangSC-Light,arial,'hiragino sans gb','microsoft yahei ui','microsoft yahei',simsun,sans-serif\"><div class=\"main-content\"style=\"\"><table style=\"width:100%%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3%%;max-width:30px;\"></td><td style=\"max-width:600px;\"><div id=\"cTMail-logo\"style=\"width:92px; height:25px;\"><a href=\"\"></a></div><p style=\"height:2px;background-color: #00a4ff;border: 0;font-size:0;padding:0;width:100%%;margin-top:20px;\"></p><div id=\"cTMail-inner\"style=\"background-color:#fff; padding:23px 0 20px;box-shadow: 0px 1px 1px 0px rgba(122, 55, 55, 0.2);text-align:left;\"><table style=\"width:100%%;font-weight:300;margin-bottom:10px;border-collapse:collapse;text-align:left;\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%%;max-width:30px;\"></td><td style=\"max-width:480px;text-align:left;\"><h1 id=\"cTMail-title\"style=\"font-size: 20px; line-height: 36px; margin: 0px 0px 22px;\">【时光漫步】邮箱安全验证</h1><p id=\"cTMail-userName\"style=\"font-size:14px;color:#333; line-height:24px; margin:0;\">尊敬的%s用户，您好！</p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">感谢您使用我们的服务。您于%s进行账号敏感操作</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">为了保护您的账户安全，我们需要确认是您本人操作。</span></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\">请在相应页面中输入以下验证码确认操作。<span style=\"font-weight: bold;\">非本人操作可忽略。</span></span></p><p class=\"cTMail-content\"style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 24px; margin: 6px 0px 0px; word-wrap: break-word; word-break: break-all;\"><p style=\"color: rgb(0, 164, 255);text-align: center;font-size: 3.125rem;font-family: 'OS_Sans';\">%s</p></p><p class=\"cTMail-content\"style=\"line-height: 24px; margin: 6px 0px 0px; overflow-wrap: break-word; word-break: break-all;\"><span style=\"color: rgb(51, 51, 51); font-size: 14px;\"><br>无法正常显示？请及时联系我们<br><a href=\"mailto:admin@bilicute.com\"title=\"\"style=\"color: rgb(0, 164, 255); text-decoration: none; word-break: break-all; overflow-wrap: normal; font-size: 14px;\">admin@bilicute.com</a></span></p><dl style=\"font-size: 14px; color: rgb(51, 51, 51); line-height: 18px;\"><dd style=\"margin: 0px 0px 6px; padding: 0px; font-size: 12px; line-height: 22px;\"><p id=\"cTMail-sender\"style=\"text-align:right;font-size: 14px; line-height: 26px; word-wrap: break-word; word-break: break-all; margin-top: 32px;\">此致<br><strong>TimeSpace</strong></p></dd></dl></td><td style=\"width:3.2%%;max-width:30px;\"></td></tr></tbody></table></div><div id=\"cTMail-copy\"style=\"text-align:center; font-size:12px; line-height:18px; color:#999\"><table style=\"width:100%%;font-weight:300;margin-bottom:10px;border-collapse:collapse\"><tbody><tr style=\"font-weight:300\"><td style=\"width:3.2%%;max-width:30px;\"></td><td style=\"max-width:540px;\"><p style=\"text-align:center; margin:20px auto 14px auto;font-size:12px;color:#999;\">此为系统邮件，请勿回复。</p><p id=\"cTMail-rights\"style=\"max-width: 100%%; margin:auto;font-size:12px;color:#999;text-align:center;line-height:22px;\"><img border=\"0\"src=\"https://img1.i-nmb.cn/everlast.love/blog.png\"style=\"width:128px; height:128px; margin:0 auto;\"><br>扫一扫或<a href=\"\"style=\"color: #999; text-decoration: none; word-break: break-all; overflow-wrap: normal;\">点击这里</a>，查看开发者博客<br><p style=\"padding-top: 20px;;margin: 0;font-family: 'OS_Sans';\">Copyright©2022-2023 iNMB&TimeSpace</p><p style=\"margin: 0;font-family: 'OS_Sans';\">All Rights Reserver.i囡漫笔版权所有</p></p></td><td style=\"width:3.2%%;max-width:30px;\"></td></tr></tbody></table></div></td><td style=\"width:3%%;max-width:30px;\"></td></tr></tbody></table></div></div></span></div></div></div></includetail></div></body></html>"
                ,userName,formattedDate,code);


        //设置自定义发件人昵称
        String nickname="";
        try {
            nickname= MimeUtility.encodeText("TimeSpace");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            //创建一个 MimeMessageHelper 对象实例，用于辅助构建 MIME 类型的信息。
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            //设置邮件接收人的邮箱地址。
            helper.setTo(toEmailAddress);
            //设置邮件发送人的地址，此处应替换成真实地发送人邮箱地址。
            helper.setFrom(new InternetAddress(nickname+" <"+adminAddress+">"));
//            设置邮件的主题。
            helper.setSubject("邮箱确认");
            //设置邮件的文本内容，其中使用了 HTML 标签来设置文本样式。具体来说，用了 <span> 标签来设置字体颜色，name 和 date 变量则是用于动态显示的内容。
            helper.setText(mailText,true);
            //发送附件
//            helper.addAttachment("文件名",new File("路径"));
            //发送图片
//            helper.setText("内容<br><img src='图片名'>",true);
//            helper.addInline("图片名",new FileSystemResource(new File("路径")));
            //超链接
//            helper.setText("内容<a href='http://www.baidu.com'>点我</a>",true);
            //调用 JavaMailSender 的 send() 方法来完成邮件发送操作，其中 mimeMessage 参数即为构建好的 MIME 类型信息对象。
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            //：捕获可能发生的异常并抛出运行时异常，以便能够及时处理问题。
            throw new RuntimeException(e);
        }
    }

}
