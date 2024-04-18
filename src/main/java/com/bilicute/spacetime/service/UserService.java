package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
     //注册
    void register(String username,String password,String email,String phone);

    //更新
    void update(User user);
}
