package com.bilicute.spacetime.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @所属包名: com.bilicute.spacetime.utils
 * @类名: PhoneCodeTool
 * @作者: i囡漫笔
 * @描述: 手机验证码相关小工具
 * @创建时间: 2024-04-18 09:32
 */


public class PhoneCodeTool {
    public static String mobileEncrypt(String mobile){
        return mobile.replaceAll("(\\d{2})\\d{7}(\\d{2})","$1*******$2");
    }

    public static String getPhoneCode(){
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

}
