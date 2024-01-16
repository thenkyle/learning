package com.school.learning.controller;

import com.school.learning.config.JwtTokenProvider;
import com.school.learning.constants.UserAuthority;
import com.school.learning.controller.dto.request.UserReq;
import com.school.learning.controller.dto.response.RspLogin;
import com.school.learning.entity.UserInfo;
import com.school.learning.service.UserInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "使用者資訊")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 取得所有使用者資訊
     */
    @GetMapping("/query")
    public List<UserInfo> getUsers() {
        List<UserInfo> res = this.userInfoService.getUsers();
        return res;
    }

    /**
     * 使用者登入
     */
    @PostMapping("/login")
    public RspLogin postUserLogin(@RequestBody UserReq userReq) {
        boolean isLogin = this.userInfoService.postUserLogin(userReq);
        UserAuthority authority = this.userInfoService.getUserByAccount(userReq.getAccount()).get().getAuthority();
        if (isLogin) {
            String token = this.jwtTokenProvider.generateToken(userReq.getAccount());
            RspLogin rspData = new RspLogin(userReq.getAccount(), authority, token);
            return rspData;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
