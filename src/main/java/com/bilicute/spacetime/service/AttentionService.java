package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Attention;
import com.bilicute.spacetime.quickMethods.QuickMethods;

public interface AttentionService {
    com.bilicute.spacetime.quickMethods.QuickMethods quickMethodsInstance = new com.bilicute.spacetime.quickMethods.QuickMethods();

    void addAttention(Integer passiveUserId);
    public default void deleteFollowRelation(Integer activeUserId, Integer passiveUserId) {
        // 检查参数是否有效
        if (activeUserId == null || passiveUserId == null) {
            throw new IllegalArgumentException("User IDs cannot be null");
        }
        // 这里是实际的数据库操作逻辑，比如使用JPA, MyBatis, JDBC等执行DELETE操作  
        // 示例：DELETE FROM follow_relations WHERE active_user = ? AND passive = ?  
        System.out.println("Deleting follow relation from user " + activeUserId + " to user " + passiveUserId);
    }

    // 假设这是获取当前登录用户ID的方法  
    public static Integer getCurrentLoggedInUserId() {
        // 这里是获取当前登录用户ID的逻辑，可能是从会话、令牌、缓存等地方获取  
        return QuickMethods.getLoggedInUserId(); // 示例调用  
    }

    // 用户取关操作的方法  
    public default void unfollowUser(Integer passiveUserIdToUnfollow) {
        // 获取当前登录用户的ID  
        Integer currentUserId = getCurrentLoggedInUserId();

        // 检查当前用户ID和被取关用户ID是否为空  
        if (currentUserId == null || passiveUserIdToUnfollow == null) {
            throw new IllegalArgumentException("User IDs cannot be null");
        }

        // 删除关注关系记录  
        deleteFollowRelation(currentUserId, passiveUserIdToUnfollow);
    }
}
