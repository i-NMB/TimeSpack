package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.VerifyCode;
import com.bilicute.spacetime.service.IVerifyCodeGen;
import com.bilicute.spacetime.service.impl.SimpleCharVerifyCodeGenImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: VerifyCodeController
 * @作者: i囡漫笔
 * @描述: 发送验证码(处理获取验证码请求)的VerifyCodeController
 * @创建时间间: 2024-04-16 16:28
 */

@CrossOrigin(origins = {"127.0.0.1:8848"}, maxAge = 3600, allowCredentials = "true")
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
            log.warn("错误"+e);
        }
    }
}
