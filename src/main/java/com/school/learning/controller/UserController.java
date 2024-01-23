package com.school.learning.controller;

import com.school.learning.controller.dto.request.LoginReq;
import com.school.learning.controller.dto.request.UserReq;
import com.school.learning.controller.dto.response.RspLogin;
import com.school.learning.entity.AppUser;
import com.school.learning.service.JwtTokenService;
import com.school.learning.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "使用者資訊")
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Operation(summary = "建立使用者")
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserReq userReq) {
        this.userService.createUser(userReq);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "取得所有使用者資訊")
    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> users = this.userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "取得指令使用者資訊")
    @GetMapping("/{username}")
    public ResponseEntity<AppUser> getUser(@PathVariable String username) {
        AppUser user = this.userService.getUser(username);
        return null == user ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }


}
