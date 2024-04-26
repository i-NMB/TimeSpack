package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.service.AttentionService;
import com.bilicute.spacetime.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attention")
public class AttentionController {
    private static AttentionService attentionService;
    @Autowired
    private void setAttentionService(AttentionService attentionService) {
        AttentionController.attentionService = attentionService;
    }
    @PostMapping
    public Result addAttention(Integer activeUserId, Integer passiveUserId) {

        try {
            attentionService.addAttention(activeUserId, passiveUserId);
            return Result.success();
        } catch (UserNotFoundException e) {
            return Result.error(e.getMessage());
        }
    }
}
