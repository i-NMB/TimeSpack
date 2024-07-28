package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.quickMethods.VerifyCode;
import com.bilicute.spacetime.service.AttentionService;
import com.bilicute.spacetime.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/attention")
public class AttentionController {

    private static AttentionService attentionService;
    @Autowired
    private void setAttentionService(AttentionService attentionService) {
        AttentionController.attentionService = attentionService;
    }

    private static UserService userService;
    @Autowired
    private void setUserService(UserService userService) {
        AttentionController.userService = userService;
    }


    //用户关注
    @PostMapping("/follow")
    public Result<String> follow(@RequestParam("followedUserId") Integer followedUserId,
                                 HttpServletRequest request, HttpServletResponse response) {
        //敏感操作，判断操作之前是否修改过密码
        if(QuickMethods.isUpdatedPassword()){
            VerifyCode.clearCachedUsers(request,response);
            return Result.error("账号过期，请重新登录");
        }
        User passiveUser=userService.findByUserId(followedUserId);
        // 判断该用户是否存在
        if(passiveUser==null){
            return Result.error("关注对象不存在");
        }
        attentionService.addAttention(followedUserId);
        return Result.success();
    }

    @PostMapping("/unfollow")
    public Result<String> unfollowUser(@RequestParam Integer passiveUserIdToUnfollow,
                                       HttpServletRequest request, HttpServletResponse response) {
        //敏感操作，判断操作之前是否修改过密码
        if(QuickMethods.isUpdatedPassword()){
            VerifyCode.clearCachedUsers(request,response);
            return Result.error("账号过期，请重新登录");
        }
        User passiveUser=userService.findByUserId(passiveUserIdToUnfollow);
        // 判断该用户是否存在
        if(passiveUser==null){
            return Result.error("操作对象不存在");
        }
        try {
            attentionService.unfollowUser(passiveUserIdToUnfollow);
            return  Result.success();
        } catch (Exception e) {
            return  Result.error("取关操作失败"+e);
        }
    }

}
