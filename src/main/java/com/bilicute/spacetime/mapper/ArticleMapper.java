package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content, cover_img, state, category_id, create_user, create_time, update_time, auditing_state, likes, view) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime}, #{auditingState}, #{likes}, #{view})")
    void add(Article article);
}
