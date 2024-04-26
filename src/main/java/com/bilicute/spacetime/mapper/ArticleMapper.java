package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    //TODO 查询自身所有文章的点赞和阅览
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
    //TODO 文章阅读量的计数
    @Update("UPDATE article SET view = view + 1 WHERE article_id = #{id}")
    void incrementViewCount(Integer id);

    @Insert("insert into active_user(user_id, followed_user_id) values(#{userId}, #{followedUserId})")
    void follow(@Param("userId") Integer userId, @Param("followedUserId") Integer followedUserId);


}
