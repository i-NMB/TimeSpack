package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ArticleMapper {
    @Insert("INSERT INTO article (title, content, cover_img, state, category_id, create_user, create_time, update_time, auditing_state, likes, view) " +
            "VALUES (#{title}, #{content}, #{coverImg}, #{state}, #{categoryId}, #{createUser}, #{createTime}, #{updateTime}, #{auditingState}, #{likes}, #{view})")
    void add(Article article);


    List<Article> list(Integer categoryId, String state,Boolean auditingState);

    @Select("select * from article where article_id = #{id}")
    Article findByUserid(Integer id);

    @Update("update article set view = view+1 where article_id = #{id}")
    void view(Integer id);

    @Update("update article set likes = likes+1 where article_id = #{id}")
    void like(Integer id);

    @Select("SELECT SUM(likes) AS total_likes " +
            "FROM article " +
            "WHERE create_user = #{loggedInUserId}")
    Integer queryLikeSelfInfo(Integer loggedInUserId);

    @Update("update article set auditing_state = #{auditingState} where article_id = #{id}")
    void check(Integer id,Boolean auditingState);

    @Select("SELECT SUM(view) AS total_view " +
            "FROM article " +
            "WHERE create_user = #{loggedInUserId}")
    Integer queryViewSelfInfo(Integer loggedInUserId);

    @Delete("DELETE FROM article WHERE article_id = #{id}")
    void delete(Integer id);


    List<Article> listWeighting(Integer categoryId, String state, boolean auditingState);

    @Update("update article set " +
            "title=#{title} " +
            ",content=#{content}" +
            ",cover_img=#{coverImg}" +
            ",state=#{state} " +
            ",category_id=#{categoryId} " +
            ",update_time=#{updateTime} " +
            "where article_id=#{articleId}")
    void update(Article article);

    List<Article> listByOneself(Integer categoryId, String state, Integer userId);
}
