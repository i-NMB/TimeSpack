package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    //根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    //添加
    @Insert("insert into user(username,password,nickname,phone,email,create_time,update_time,identity)"+
            "values(#{username},#{password},#{username},#{phone},#{email},now(),now(),#{identity})")
    void add(String username,String password,String email,String phone,String identity);

    @Update("update user set username=#{username},password=#{password},nickname=#{nickname},phone=#{phone},email=#{email},user_pic=#{userPic},create_time=#{createTime},update_time=#{updateTime} where create_user=#{createUser}")
    void update(User user);
}
