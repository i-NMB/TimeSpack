package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.AttentionMapper;
import com.bilicute.spacetime.pojo.Attention;
import com.bilicute.spacetime.service.AttentionService;
import com.bilicute.spacetime.exception.UserNotFoundException;
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
    public void addAttention(Integer activeUserId, Integer passiveUserId) {
        // 检查用户ID是否存在
        if (!checkUserIdExists(activeUserId) || !checkUserIdExists(passiveUserId)) {
            throw new UserNotFoundException("用户ID不存在");
        }
        Attention attention = new Attention();
        attention.setActiveUserId(activeUserId);
        attention.setPassiveUserId(passiveUserId);
        attention.setAttentionTime(LocalDateTime.now());
        attentionMapper.add(attention);
    }

    private boolean checkUserIdExists(Integer userId) {
        // 假设这里通过用户ID查询数据库来检查用户是否存在
        boolean exists = false;
        // 查询数据库是否存在该ID的用户
        // Example: List<User> users = userMapper.findByUserId(userId);
        // exists = (!users.isEmpty());

        // 临时示例逻辑，实际应替换为真实的数据库查询逻辑
        exists = true; // 假设总是存在

        return exists;
    }
}
