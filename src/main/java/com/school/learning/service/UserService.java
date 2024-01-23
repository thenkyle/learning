package com.school.learning.service;

import com.school.learning.config.SecurityConfig;
import com.school.learning.controller.dto.request.LoginReq;
import com.school.learning.controller.dto.request.UserReq;
import com.school.learning.entity.AppUser;
import com.school.learning.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityConfig securityConfig;

    /** 新增使用者資料*/
    public boolean createUser(UserReq userReq) {
        AppUser user = new AppUser();
        user.setUsername(userReq.getUsername());
        user.setPassword(this.securityConfig.passwordEncoder().encode(userReq.getPassword()));
        user.setAuthority(userReq.getAuthority());
        this.userRepository.save(user);
        return true;
    }
    /**取得所有使用者資料*/
    public List<AppUser> getUsers(){
        List<AppUser> users = this.userRepository.findAll();
        return users;
    }
    /**取得指定使用者資料*/
    public AppUser getUser(String username){
        AppUser user = this.userRepository.findByUsername(username);
        return user;
    }

    /**使用者登入+驗證密碼
     * 此方法比較直白，但如果使用AuthenticationProvider會更有彈性
     * */
    public boolean login(LoginReq loginReq){
        AppUser user = this.userRepository.findByUsername(loginReq.getUsername());
        return null == user
                ? false
                : this.securityConfig.passwordEncoder().matches(loginReq.getPassword(), user.getPassword());
    }
}
