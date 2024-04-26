package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (commentator_id, content, state, article_id, creat_user, create_time, auditing_state, likes)"+
             "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, #{createUser}, #{auditingState}, #{likes})")
    void add(Comment comment);


    @Delete("INSERT INTO comment (commentator_id, content, state, article_id, creat_user, create_time, auditing_state, likes)"+
            "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, #{createUser}, #{auditingState}, #{likes})")
    void delete(Comment comment);

    @Insert("INSERT INTO comment (commentator_id, content, state, article_id, creat_user, create_time, auditing_state, likes)"+
            "VALUES (#{commentatorId}, #{content}, #{state}, #{articleId}, #{createUser}, #{createUser}, #{auditingState}, #{likes})")
    void save(Comment comment);


}

