package com.bilicute.spacetime.quickMethods;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @所属包名: com.bilicute.spacetime.quickMethods
 * @类名: VerifyCode
 * @作者: i囡漫笔
 * @描述: 验证各类验证码的便捷实现类
 * @创建时间: 2024-04-18 15:50
 */


public class VerifyCode {
    /**
     * @param request:   网络请求（HttpServletRequest request）
     * @param phone:     手机号
     * @param phoneCode: 手机验证码
     * @return Boolean(若为true, 则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByPhone(HttpServletRequest request, String phone, String phoneCode) {
        //判断手机号是否是请求时候的手机号
        if (!phone.equals(request.getSession().getAttribute("phone"))) {
            return false;//如果不是求时候的手机号则返回验证失败
        }
        if (phoneCode == null) {
            return false;
        }
        System.out.println("传递的手机验证码为" + request.getSession().getAttribute("PhoneCode"));
        //返回是否验证通过
        return phoneCode.equals(request.getSession().getAttribute("PhoneCode"));
    }


    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param code:    图像验证码
     * @return Boolean(若为true, 则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByImg(HttpServletRequest request, String code) {
        code = code.toUpperCase();//小写字母转为大写
        return code.equals(request.getSession().getAttribute("VerifyCode"));//返回是否验证通过
    }


    /**
     * @param request:   网络请求（HttpServletRequest request）
     * @param email:     邮箱地址
     * @param emailCode: 邮箱验证码
     * @return Boolean(若为true, 则通过验证)
     * @author i囡漫笔
     * @description 通过对发送验证码时的网络请求request来获取Session，对比Session中记录的验证码和用户输入的验证码返回是否通过验证
     * @date 2024/4/18
     */
    public static Boolean verifyByMail(HttpServletRequest request, String email, String emailCode) {
        //判断邮箱地址是否是请求时候的邮箱地址
        if (!email.equals(request.getSession().getAttribute("Mail"))) {
            return false;
        }
        if (emailCode == null) {
            return false;
        }
        emailCode = emailCode.toUpperCase();//小写字母转为大写
        return emailCode.equals(request.getSession().getAttribute("MailCode"));//返回是否验证通过
    }


    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param phone: 已登陆用户的手机号
     * @param phoneCode: 手机验证码
     * @return Boolean
     * @author i囡漫笔
     * @description 验证已登陆用户的手机号
     * @date 2024/4/24
     */
    public static Boolean verifyByPhoneInLoggedUser(HttpServletRequest request, String phone, String phoneCode) {
        //判断手机号是否是请求时候的手机号
        if (!phone.equals(request.getSession().getAttribute("loggedInPhone"))) {
            return false;//如果不是求时候的手机号则返回验证失败
        }
        if (phoneCode == null) {
            return false;
        }
        //返回是否验证通过
        return phoneCode.equals(request.getSession().getAttribute("PhoneCode"));
    }


    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param email: 已登陆用户的邮箱地址
     * @param emailCode: 邮箱验证码
     * @return Boolean
     * @author i囡漫笔
     * @description 验证已登陆用户的邮箱
     * @date 2024/4/24
     */
    public static Boolean verifyByMailInLoggedUser(HttpServletRequest request, String email, String emailCode) {
        //判断邮箱地址是否是请求时候的邮箱地址
        if (!email.equals(request.getSession().getAttribute("loggedInEmail"))) {
            return false;
        }
        if (emailCode == null) {
            return false;
        }
        emailCode = emailCode.toUpperCase();//小写字母转为大写
        return emailCode.equals(request.getSession().getAttribute("MailCode"));//返回是否验证通过
    }

    /**
     * @param request: 网络请求（HttpServletRequest request）
     * @param response: 网络响应
     * @author i囡漫笔
     * @description 清除cookie登陆信息
     * @date 2024/7/29
     */
    public static void clearCachedUsers(HttpServletRequest request, HttpServletResponse response){
        // 获取用户传回的所有cookies
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0); // expires in 7 days
        // cookie.setSecure(true); //安全cookie是仅通过加密的HTTPS连接发送到服务器的cookie。安全Cookie不能通过未加密的HTTP连接传输到服务器。
        cookie.setHttpOnly(true); //当为cookie设置HttpOnly标志时，它会告诉浏览器只有服务器才能访问此特定cookie。
        cookie.setPath("/");
        response.addCookie(cookie);
        request.getSession().invalidate();
    }
}