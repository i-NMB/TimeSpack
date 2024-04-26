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

}
