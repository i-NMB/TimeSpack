package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

// CommentMapper.java
@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (article_id, user_id, content, create_time) " +
            "VALUES (#{articleId}, #{userId}, #{content}, #{createTime})")
    void add(Comment comment);
}
