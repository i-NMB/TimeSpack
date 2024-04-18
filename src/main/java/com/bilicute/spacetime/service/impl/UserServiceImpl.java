package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.UserMapper;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Md5Util;
import com.bilicute.spacetime.utils.Sha256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;

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
    public void register(String username,String password,String email,String phone) {
        //加密
        String sha256String = Sha256.addSalt(password);
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            // 分割电子邮件地址以获取用户名和域名
            String[] parts = email.split("@");
            String domain = parts[1];
            if (domain.equals("bilicute.com")){
                userMapper.add(username,sha256String,email,phone,"Admin");
                return;
            }
        }
        //添加
        userMapper.add(username,sha256String,email,phone,"User");
    }

    @Override
    public void update(User user) {
        user.setUpdateTime(String.valueOf(LocalDateTime.now()));
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
//        Map<String,Object> map=ThreadLocalUtil.get();
//        Integer createUser=(Integer) map.get("createUser");
//        userMapper.updateAvatar(avatarUrl,createUser);
    }
}
