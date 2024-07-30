package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @所属包名: com.bilicute.spacetime.mapper
 * @类名: CategoryMapper
 * @作者: i囡漫笔
 * @描述: Category分类的Mapper层用来与数据库连接
 * @创建时间: 2024-04-24 10:15
 */

@Mapper
public interface CategoryMapper {

    @Select("select * from category where category_name=#{categoryName}")
    Category findByName(String categoryName);

    @Insert("insert into category(category_id,category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryId},#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    //查询用户id所创的所有分类
    @Select("select * from category")
    List<Category> list();

    @Select("select * from category where category_alias=#{categoryAlias}")
    Category findByAliasName(String categoryAlias);

    @Select("select * from category where category_id = #{id}")
    Category findById(Integer id);

    @Update("update category set " +
            "category_name=#{categoryName} " +
            ",category_alias=#{categoryAlias}" +
            ",update_time=#{updateTime} " +
            "where category_id=#{categoryId}")
    void update(Category category);

    //删除
    @Delete("DELETE FROM category WHERE category_id = #{id}")
    void delete(Integer id);
}
