package com.bilicute.spacetime.quickMethods;

import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取已登陆用户实体
 * User user = QuickMethods.getLoggedUser();
 * 获取已登陆用户username
 * String username = QuickMethods.getLoggedInUserName();
 * 获取已登陆用户id
 * Integer id = QuickMethods.getLoggedInUserId();
 * 获取已登陆用户的邮箱
 * String email = QuickMethods.getLoggedInEmail();
 * 获取已登陆用户的手机号
 * String phone = QuickMethods.getLoggedInPhone();
 */
@Component
public class QuickMethods {


    private static UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        QuickMethods.userService=userService;
    }

    public static String getLoggedInUserName(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        return (String) jwtMap.get("username");
    }

    public static Integer getLoggedInUserId(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        return (Integer) jwtMap.get("id");
    }

    public static String getLoggedInEmail(){
        User user = getLoggedUser();
        return user.getEmail();
    }

    public static String getLoggedInPhone(){
        User user = getLoggedUser();
        return user.getPhone();
    }

    public static User getLoggedUser(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        String userName = (String) jwtMap.get("username");
        return userService.findByUserName(userName);
    }

    public static Boolean isAdmin(){
        User user = getLoggedUser();
        String identity = user.getIdentity();
        return identity.equals("Admin");
    }

    public static String getLoggedInNickname(){
        User user = getLoggedUser();
        return user.getNickname();
    }

}
