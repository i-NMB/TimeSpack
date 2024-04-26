package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.AttentionService;
import com.bilicute.spacetime.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result follow(@RequestParam("followedUserId") Integer followedUserId) {
        User passiveUser=userService.findByUserId(followedUserId);
        // 判断该用户是否存在
        if(passiveUser==null){
            return Result.error("关注对象不存在");
        }
        attentionService.addAttention(followedUserId);
        return Result.success();
    }
    @PostMapping("/unfollow")
    public Result unfollowUser(@RequestParam Integer passiveUserIdToUnfollow) {
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
