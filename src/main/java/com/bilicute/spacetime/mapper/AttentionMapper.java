package com.bilicute.spacetime.mapper;

import com.bilicute.spacetime.pojo.Attention;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttentionMapper {
    @Insert("insert into attention(active_user, passive_user, attention_time)" +
            "values(#{activeUserId}, #{passiveUserId}, #{attentionTime})")
    void add(Attention attention);
    @Delete("delete from attention(active_user, passive_user, attention_time)" +
            "values(#{activeUserId}, #{passiveUserId}, #{attentionTime}) ")
    void delete(Integer active_user);
}
