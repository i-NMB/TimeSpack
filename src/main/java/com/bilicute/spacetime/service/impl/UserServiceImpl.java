package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.UserMapper;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public User findByUserId(Integer id) {
        return userMapper.findByUserId(id);
    }

    @Override
    public void register(String username,String password,String email,String phone) {
        //加密
        String sha256String = Sha256.addSalt(password);
        String emailRegex = "^[a-zA-Z\\d_+&*-]+(?:\\.[a-zA-Z\\d_+&*-]+)*@(?:[a-zA-Z\\d-]+\\.)+[a-zA-Z]{2,7}$";
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
    public void updateAvatar(String avatarUrl, Integer loggedInUserId) {
        userMapper.updateAvatar(avatarUrl, loggedInUserId);
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer createUser=(Integer) map.get("id");
        userMapper.updatePwd(Sha256.addSalt(newPwd),createUser);
    }

    @Override
    public void updateMail(String mail) {
        userMapper.updateMail(mail,QuickMethods.getLoggedInUserId());
    }



    @Override
    public void updateNickname(String nickname, Integer id) {
        userMapper.addNickname(nickname,id);
    }


    @Override
    public void updatePhone(String phone) {
        userMapper.updatePhone(phone,QuickMethods.getLoggedInUserId());
    }


}
