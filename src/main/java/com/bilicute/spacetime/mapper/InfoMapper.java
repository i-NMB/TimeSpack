package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InfoMapper {
    @Select("SELECT * FROM article WHERE create_user = #{userId} AND view = (SELECT MAX(view) FROM article WHERE create_user = #{userId})")
    Article getMaxViewArticle(Integer userId);
    @Select("SELECT * FROM article WHERE create_user = #{userId} AND likes = (SELECT MAX(likes) FROM article WHERE create_user = #{userId})")
    Article getLikeViewArticle(Integer userId);

    @Select("SELECT * FROM comment WHERE create_user = #{userId} AND likes = (SELECT MAX(likes) FROM comment WHERE create_user = #{userId})")
    Comment getLikeViewComment(Integer userId);

    @Select("SELECT COUNT(article_id) AS total_articles FROM article WHERE create_user = #{userId}")
    Integer allArticle(Integer userId);

    @Select("SELECT COUNT(commentator_id) AS total_comments FROM comment WHERE create_user = #{userId}")
    Integer allComment(Integer userId);

    @Select("SELECT SUM(view) AS total_views FROM article WHERE create_user = #{userId};")
    Integer allViewByArticle(Integer userId);

    @Select("SELECT SUM(likes) AS total_views FROM comment WHERE create_user = #{userId};")
    Integer allLikeByComment(Integer userId);

    @Select("SELECT SUM(likes) AS total_views FROM article WHERE create_user = #{userId};")
    Integer allLikeByArtiCle(Integer userId);
}
