package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.UserMapper;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Md5Util;
import com.bilicute.spacetime.utils.Sha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String sha256String = Sha256.addSalt(password);
        //添加
        userMapper.add(username,sha256String);

    }

    @Override
    public void update(User user) {
        user.setUpdateTime(String.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }
}
