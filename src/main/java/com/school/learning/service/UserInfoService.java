package com.school.learning.service;

import com.school.learning.controller.dto.request.UserReq;
import com.school.learning.entity.UserInfo;
import com.school.learning.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 取得所有使用者資訊
     */
    public List<UserInfo> getUsers() {
        List<UserInfo> users = this.userInfoRepository.findAll();
        return users;
    }

    /**
     * 取得指定User資訊
     */
    public Optional<UserInfo> getUserByAccount(String account) {
        Optional<UserInfo> user = this.userInfoRepository.findByAccount(account);
        return user;
    }

    /**
     * 使用者登入
     */
    public boolean postUserLogin(UserReq userReq) {
        // step1.找出DB內的account.
        // Optional<T>可以判斷、get T的內容
        Optional<UserInfo> user = this.userInfoRepository.findByAccount(userReq.getAccount());
        // step2.找出後的邏輯(Optional<T>可以判斷、get T的內容).
        if (user.isPresent()) {
            UserInfo userInfo = user.get();
            boolean isDecode = passwordEncoder.matches(userReq.getPassword(), userInfo.getPassword());
            return isDecode;
        }
        return false;
    }
}
