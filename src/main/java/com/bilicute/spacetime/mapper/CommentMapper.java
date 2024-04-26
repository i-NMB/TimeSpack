package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (commentator_id, content, state, article_id, create_user, create_time, auditing_state, likes)"+
             "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, #{createTime}, #{auditingState}, #{likes})")
    void add(Comment comment);


    @Delete("DELETE FROM comment WHERE commentator_id = #{commentId}")
    void delete(Integer commentId);


    @Select("SELECT COUNT(commentator_id) FROM comment WHERE article_id=#{articleId} and auditing_state=1")
    Integer getCommentNumber(Integer articleId);

    List<Comment> list(Integer articleId, Boolean auditingState);

    @Select("select * from comment where commentator_id=#{commentId}")
    Comment findById(Integer commentId);

    @Update("update comment set auditing_state = #{auditingState} where commentator_id = #{id}")
    void check(Integer id,Boolean auditingState);
}

