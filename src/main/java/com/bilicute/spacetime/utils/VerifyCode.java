package com.bilicute.spacetime.utils;

import com.bilicute.spacetime.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @所属包名: com.bilicute.spacetime.utils
 * @类名: VerifyCode
 * @作者: i囡漫笔
 * @描述: 验证各类验证码的便捷实现类
 * @创建时间: 2024-04-18 15:50
 */


public class VerifyCode {
    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param phone: 手机号
     * @param phoneCode: 手机验证码
     * @return Boolean(若为true,则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByPhone(HttpServletRequest request,String phone,String phoneCode){
        //判断手机号是否是请求时候的手机号
        if(!phone.equals(request.getSession().getAttribute("phone"))){
            return false;//如果不是求时候的手机号则返回验证失败
        }
        //返回是否验证通过
        return phoneCode.equals(request.getSession().getAttribute("PhoneCode"));
    }

    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param code: 图像验证码
     * @return Boolean(若为true,则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByImg(HttpServletRequest request,String code){
        code = code.toUpperCase();//小写字母转为大写
        return code.equals(request.getSession().getAttribute("VerifyCode"));//返回是否验证通过
    }

    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param email: 邮箱地址
     * @param emailCode: 邮箱验证码
     * @return Boolean(若为true,则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByMail(HttpServletRequest request,String email,String emailCode){
        //判断邮箱地址是否是请求时候的邮箱地址
        if(!email.equals(request.getSession().getAttribute("Mail"))){
            return false;
        }
        emailCode = emailCode.toUpperCase();//小写字母转为大写
        return emailCode.equals(request.getSession().getAttribute("MailCode"));//返回是否验证通过
    }
}
