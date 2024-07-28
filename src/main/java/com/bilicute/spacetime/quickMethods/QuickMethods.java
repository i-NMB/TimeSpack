package com.bilicute.spacetime.quickMethods;

import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QuickMethods {

    private static UserService userService;
    @Autowired
    public void setUserService(UserService userService){
        QuickMethods.userService=userService;
    }

    /**
     * @return String
     * @author i囡漫笔
     * @description 快速方法以获取已登陆用户的用户名
     * {@code 获取用户名}
     * @date 2024/7/28
     */
    public static String getLoggedInUserName(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        return (String) jwtMap.get("username");
    }

    /**
     * @return Integer
     * @author i囡漫笔
     * @description 快速方法以获取已登陆用户的ID
     * {@code 获取id}
     * @date 2024/7/28
     */
    public static Integer getLoggedInUserId(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        return (Integer) jwtMap.get("id");
    }

    /**
     * @return String
     * @author i囡漫笔
     * @description 快速方法以获取已登陆用户的邮箱
     * {@code 获取邮箱}
     * @date 2024/7/28
     */
    public static String getLoggedInEmail(){
        User user = getLoggedUser();
        return user.getEmail();
    }

    /**
     * @return String
     * @author i囡漫笔
     * @description 快速方法以获取已登陆用户的手机号
     * {@code 手机号}
     * @date 2024/7/28
     */
    public static String getLoggedInPhone(){
        User user = getLoggedUser();
        return user.getPhone();
    }

    /**
     * @return User
     * @author i囡漫笔
     * @description 获取用户实体
     * @date 2024/7/28
     */
    public static User getLoggedUser(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        String userName = (String) jwtMap.get("username");
        return userService.findByUserName(userName);
    }

    /**
     * @return Boolean
     * @author i囡漫笔
     * @description 判断已登陆用户是否为管理员
     * {@code 管理员判断}
     * @date 2024/7/28
     */
    public static Boolean isAdmin(){
        User user = getLoggedUser();
        String identity = user.getIdentity();
        return identity.equals("Admin");
    }

    /**
     * @return String
     * @author i囡漫笔
     * @description 获取已登陆用户的别称
     * {@code 别称获取}
     * @date 2024/7/28
     */
    public static String getLoggedInNickname(){
        User user = getLoggedUser();
        return user.getNickname();
    }

    /**
     * @return
     * {@code Boolean} 检测（在操作前）是否已经更新密码，如果更新密码返回
     * {@code true}(返回此状态时需要清理cookie使jwt失效) ，如果没有修改密码则返回
     * {@code false}(返回此状态时可以正常处理业务)
     * @author i囡漫笔
     * @description 检测密码是否更新
     * {@code 在敏感操作时检测}
     * @date 2024/7/28
     */
    public static Boolean isUpdatedPassword(){
        Map<String,Object> jwtMap = ThreadLocalUtil.get();
        String sha2password = (String) jwtMap.get("SHA2password");
        User user = getLoggedUser();
        return !sha2password.equals(Sha256.addSalt(user.getPassword()));
    }

}
