package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.User;

public interface UserService {
    //根据用户名查询用户
    User findByUserName(String username);
    User findByUserId(Integer id);
     //注册
    void register(String username,String password,String email,String phone);


    //更新头像
    void updateAvatar(String avatarUrl, Integer loggedInUserId);
//更新密码
    void updatePwd(String newPwd);

    void updateMail(String mail);

    void updateNickname(String nickname,Integer id);

    void updatePhone(String phone);
}
