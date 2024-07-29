package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.quickMethods.VerifyCode;
import com.bilicute.spacetime.service.AttentionService;
import com.bilicute.spacetime.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     * @param passiveId 被关注用户的id
     * @return Result<String>
     * @description 关注功能
     * @author myd
     */
    @GetMapping("/concern")
    public Result<String> concern(Integer passiveId,
                                  HttpServletRequest request, HttpServletResponse response) {
        if (passiveId == null) {
            return Result.error("关键数据缺失");
        }
        //敏感操作，判断操作之前是否修改过密码
        if(QuickMethods.isUpdatedPassword()){
            VerifyCode.clearCachedUsers(request,response);
            return Result.error("账号过期，请重新登录");
        }
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        attentionService.concern(loggedInUserId, passiveId);
        return Result.success();
    }

    /**
     * @param passiveId 被取消关注用户的id
     * @return Result<String>
     * @description 取消关注功能
     * @author myd
     */
    @GetMapping("/disConcern")
    public Result<String> disConcern(Integer passiveId,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (passiveId == null) {
            return Result.error("关键数据缺失");
        }
        //敏感操作，判断操作之前是否修改过密码
        if(QuickMethods.isUpdatedPassword()){
            VerifyCode.clearCachedUsers(request,response);
            return Result.error("账号过期，请重新登录");
        }
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        attentionService.disConcern(loggedInUserId, passiveId);
        return Result.success();
    }

    /**
     * @return Result<?>
     * @description 用户获得已关注用户的列表
     * @author i囡漫笔, myd
     * @date 2024/7/27
     */
    @GetMapping("/getConcern")
    public Result<?> getConcern() {
        Integer loggedInUserId = QuickMethods.getLoggedInUserId();
        List<Integer> getConcern = attentionService.getConcern(loggedInUserId);
        return Result.success(getConcern);
    }

}
