package com.bilicute.spacetime;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * @所属包名: com.bilicute.spacetime
 * @类名: TestCommentMapper
 * @作者: i囡漫笔
 * @描述: 测试类
 * @创建时间: 2024-07-30 23:50
 */

@Mapper
public interface TestCommentMapper {
    @Insert("INSERT INTO comment (commentator_id, content, state, article_id, create_user, create_time, auditing_state, likes)" +
            "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, now(), #{auditingState}, #{likes})")
    void addComment(Integer commentatorId, String content, String state, Integer articleId, Integer createUser, Boolean auditingState, Integer likes);

    @Update("update comment set auditing_state = #{auditingState} where commentator_id = #{id}")
    void uncheck(Integer id, Boolean auditingState);
}
