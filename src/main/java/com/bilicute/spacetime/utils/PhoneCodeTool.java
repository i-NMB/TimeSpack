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
    /**
     * @param mobile:  输入手机号
     * @return String
     * @author i囡漫笔
     * @description 手机号码脱敏
     * @date 2024/4/18
     */
    public static String mobileEncrypt(String mobile){
        if (mobile == null || mobile.length() != 11) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{2})\\d{7}(\\d{2})","$1*******$2");
    }

    /**
     * @return String
     * @author i囡漫笔
     * @description 随机获取6位手机验证码
     * @date 2024/4/18
     */
    public static String getPhoneCode(){
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        return String.valueOf(code);
    }

    /**
     * @param phone:  手机号
     * @return boolean
     * @author i囡漫笔
     * @description 判断输入的字符串是否为手机号
     * @date 2024/4/18
     */
    public static boolean isPhone(String phone) {
        if (phone == null) {
            return false;
        }
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
