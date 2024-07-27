package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.AttentionMapper;
import com.bilicute.spacetime.pojo.Attention;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AttentionServiceImpl implements AttentionService {
    private static AttentionMapper attentionMapper;

    @Autowired
    private void setAttentionMapper(AttentionMapper attentionMapper) {
        AttentionServiceImpl.attentionMapper = attentionMapper;
    }

    @Override
    public void addAttention(Integer passiveUserId) {
        // 检查用户ID是否存在
        Integer activeUserId = QuickMethods.getLoggedInUserId();
        Attention attention = new Attention();
        attention.setActiveUserId(activeUserId);
        attention.setPassiveUserId(passiveUserId);
        attention.setAttentionTime(LocalDateTime.now());
        attentionMapper.add(attention);
    }


    @Override
    public void unfollowUser(Integer passiveUserIdToUnfollow) {
        // 获取当前登录用户的ID
        Integer currentUserId = QuickMethods.getLoggedInUserId();
        // 检查当前用户ID和被取关用户ID是否为空
        // 删除关注关系记录
        attentionMapper.delete(currentUserId,passiveUserIdToUnfollow);

    }

}
