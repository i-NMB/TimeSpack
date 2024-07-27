package com.bilicute.spacetime.service;

public interface AttentionService {

    void addAttention(Integer passiveUserId);
    // 假设这是获取当前登录用户ID的方法

    // 用户取关操作的方法  
    void unfollowUser(Integer passiveUserIdToUnfollow);
}
