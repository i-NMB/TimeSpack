package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (commentator_id, content, state, article_id, creat_user, create_time, auditing_state, likes)"+
             "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, #{createTime}, #{auditingState}, #{likes})")
    void add(Comment comment);


    @Delete("DELETE FROM comment WHERE commentator_id = #{commentId}")
    void delete(Integer commentId);


    @Select("SELECT COUNT(commentator_id) FROM comment WHERE article_id=#{articleId} and auditing_state=1")
    Integer getCommentNumber(Integer articleId);

    List<Comment> list(Integer articleId, Boolean auditingState);
}

