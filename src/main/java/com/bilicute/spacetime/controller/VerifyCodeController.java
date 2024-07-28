package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.VerifyCode;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.IVerifyCodeGen;
import com.bilicute.spacetime.service.MailService;
import com.bilicute.spacetime.service.impl.SimpleCharVerifyCodeGenImpl;
import com.bilicute.spacetime.utils.PhoneCodeTool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.core.factory.SmsFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.bilicute.spacetime.utils.PhoneCodeTool.isPhone;
import static com.bilicute.spacetime.utils.StringUtilsFromTime.isEmptyString;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: VerifyCodeController
 * @作者: i囡漫笔
 * @描述: 发送验证码(处理获取验证码请求)的VerifyCodeController
 * @创建时间: 2024-04-16 16:28
 */

//@CrossOrigin(origins = "*")

@RestController
@RequestMapping("/getCode")
@Slf4j
public class VerifyCodeController {
    /**
     * @param request: 网络请求
     * @param response: 网络响应
     * @author i囡漫笔
     * @description 发送验证码，并将验证码放入Session
     * @date 2024/4/16
     */
    @GetMapping("/img")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        try {
            //设置长宽
            VerifyCode verifyCode = iVerifyCodeGen.generate(110, 30);
            String code = verifyCode.getCode();
//            LOGGER.info(code);
            //将VerifyCode绑定session
            request.getSession().setAttribute("VerifyCode", code);
//            System.out.println(request.getSession().getAttribute("VerifyCode"));
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            log.warn("错误", e); // log.warn方法可以接受异常对象作为第二个参数
        }
    }


    private MailService mailService;
    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * @param request: 网络请求,用于Session存放
     * @param response: 网络响应，用于设置响应头
     * @param email: 邮箱
     * @return Result
     * @author i囡漫笔
     * @description 用于新用户绑定邮箱获取邮箱验证码时使用
     * @date 2024/4/18
     */
    @PostMapping  ("/mail")
    public Result<String> MailVerifyCode(HttpServletRequest request, HttpServletResponse response,@Email String email) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        String code;
        //设置响应头
        response.setHeader("Pragma", "no-cache");
        //设置响应头
        response.setHeader("Cache-Control", "no-cache");
        //在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);
        if (request.getSession().getAttribute("RequestMailTime") != null) {
            if (System.currentTimeMillis()-(long)request.getSession().getAttribute("RequestMailTime") < 60000){
                return Result.error("60秒内请勿重复发送验证码");
            }
        }
        if (isEmptyString(email)){
            return Result.error("邮箱为空");
        }

        try (ByteArrayOutputStream streams = new ByteArrayOutputStream()) {
            code = iVerifyCodeGen.generate(1, 1, streams);
            //将VerifyCode绑定session
            request.getSession().setAttribute("RequestMailTime",System.currentTimeMillis());
            request.getSession().setAttribute("Mail",email);
            request.getSession().setAttribute("MailCode", code);
//            System.out.println("验证码为："+code);
        } catch (IOException e) {
            return Result.error("生成邮箱验证码发送错误：" + e.getMessage());
        }
            mailService.sendEmail(email, code, "用户");
        log.info("新用户发送邮件验证码：{}", email);
        return Result.success();
    }

    /**
     * @param request:
     * @param response:  [request, response]
     * @return Result
     * @author i囡漫笔
     * @description 已登陆用户获取验证码
     * @date 2024/4/24
     */
    @PostMapping  ("/mailByUser")
    public Result<String> UserMailVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        IVerifyCodeGen iVerifyCodeGen = new SimpleCharVerifyCodeGenImpl();
        String code;
        //设置响应头
        response.setHeader("Pragma", "no-cache");
        //设置响应头
        response.setHeader("Cache-Control", "no-cache");
        //在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);

        String loggedInEmail = QuickMethods.getLoggedInEmail();
        if (isEmptyString(loggedInEmail)){
            return Result.error("邮箱为空");
        }

        try (ByteArrayOutputStream streams = new ByteArrayOutputStream()) {
            code = iVerifyCodeGen.generate(1, 1, streams);
            //将VerifyCode绑定session
            request.getSession().setAttribute("loggedInEmail",loggedInEmail);
            request.getSession().setAttribute("MailCode", code);
//            System.out.println("验证码为："+code);
        } catch (IOException e) {
            return Result.error("生成邮箱验证码发送错误：" + e.getMessage());
        }
        mailService.sendEmail(loggedInEmail, code, QuickMethods.getLoggedInNickname());
        log.info("老用户发送邮件验证码，邮箱：{}", loggedInEmail);
        return Result.success();
    }

    /**
     * @param request: 网络请求,用于Session存放
     * @param response: 网络响应，用于设置响应头
     * @param phone:  手机号
     * @return Result
     * @author i囡漫笔
     * @description
     * @date 2024/4/18
     */
    @PostMapping("/phone")
    public Result<String> sendPhoneCode(HttpServletRequest request,HttpServletResponse response, String phone){
        //设置响应头
        response.setHeader("Pragma", "no-cache");
        //设置响应头
        response.setHeader("Cache-Control", "no-cache");
        //在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);
        if (request.getSession().getAttribute("RequestTime") != null) {
            if (System.currentTimeMillis()-(long)request.getSession().getAttribute("RequestTime") < 60000){
                return Result.error("60秒内请勿重复发送验证码");
            }
        }
        if(!isPhone(phone)){
            return Result.error("请输入正确的手机号");
        }
        String phoneCode = PhoneCodeTool.getPhoneCode();
        request.getSession().setAttribute("RequestTime",System.currentTimeMillis());
        request.getSession().setAttribute("phone",phone);
        request.getSession().setAttribute("PhoneCode", phoneCode);
        //发送短信
        SmsBlend smsBlend = SmsFactory.getSmsBlend("tx-register");
        smsBlend.sendMessage(phone,phoneCode);
        return Result.success();
    }

    @PostMapping("/phoneByUser")
    public Result<String> sendPhoneCodeByUser(HttpServletRequest request,HttpServletResponse response){
        //设置响应头
        response.setHeader("Pragma", "no-cache");
        //设置响应头
        response.setHeader("Cache-Control", "no-cache");
        //在代理服务器端防止缓冲
        response.setDateHeader("Expires", 0);
        if (request.getSession().getAttribute("RequestTime") != null) {
            if (System.currentTimeMillis()-(long)request.getSession().getAttribute("RequestTime") < 60000){
                return Result.error("60秒内请勿重复发送验证码");
            }
        }
        String loggedInPhone = QuickMethods.getLoggedInPhone();
        if(!isPhone(loggedInPhone)){
            return Result.error("请输入正确的手机号");
        }
        String phoneCode = PhoneCodeTool.getPhoneCode();
        request.getSession().setAttribute("RequestTime",System.currentTimeMillis());
        request.getSession().setAttribute("loggedInPhone",loggedInPhone);
        request.getSession().setAttribute("PhoneCode", phoneCode);
        //发送短信
        SmsBlend smsBlend = SmsFactory.getSmsBlend("tx-register");
        smsBlend.sendMessage(loggedInPhone,phoneCode);
        return Result.success();
    }
}
