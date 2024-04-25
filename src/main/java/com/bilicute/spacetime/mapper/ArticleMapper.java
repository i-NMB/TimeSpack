package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ArticleMapper {
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time,auditing_state,likes,view)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime},#{auditingState},#{likes},#{view})")
    void add(Article article);
}
