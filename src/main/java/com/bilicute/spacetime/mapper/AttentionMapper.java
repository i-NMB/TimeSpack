package com.bilicute.spacetime.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttentionMapper {
    @Insert("insert into attention(active_user, passive, operation_time) values(#{loggedInUserId},#{passiveId},now())")
    void concern(Integer loggedInUserId, Integer passiveId);

    @Delete("delete from attention  where passive=#{passiveId} and active_user=#{loggedInUserId}")
    void disConcern(Integer loggedInUserId, Integer passiveId);

    @Select(" select passive from attention where active_user = #{loggedInUserId}")
    List<Integer> getConcern(Integer loggedInUserId);
}
