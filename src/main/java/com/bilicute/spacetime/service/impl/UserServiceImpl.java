package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.annotate.Identity;
import com.bilicute.spacetime.mapper.UserMapper;
import com.bilicute.spacetime.pojo.User;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.UserService;
import com.bilicute.spacetime.utils.Sha256;
import com.bilicute.spacetime.utils.ThreadLocalUtil;
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
        return userMapper.findByUserName(username);
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
        Map<String,Object> map= ThreadLocalUtil.get();
        Integer createUser=(Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,createUser);
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

    @Override
    public void changePasswordByPhone(String newPassword) {

    }

    @Override
    public boolean updatePwd(String mail, String newPassword) {
        return false;
    }

}
