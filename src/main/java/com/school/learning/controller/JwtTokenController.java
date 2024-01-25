package com.school.learning.controller;

import com.school.learning.controller.dto.request.LoginReq;
import com.school.learning.controller.dto.response.RspLogin;
import com.school.learning.service.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(name = "權限相關")
@RequestMapping("/api/v1/auth")
public class JwtTokenController {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Operation(summary = "取得一組jwt token")
    @GetMapping
    public String getJwtToken() {
        LoginReq loginReq = new LoginReq("admin", "admin");
        return this.jwtTokenService.createToken(loginReq).getToken();
    }

    @Operation(summary = "使用者登入")
    @PostMapping("/login")
    public ResponseEntity<RspLogin> login(@RequestBody LoginReq loginReq) {
//        boolean isEncode = this.userService.login(loginRequest);
//        if(!isEncode){
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
        RspLogin rspLogin = this.jwtTokenService.createToken(loginReq);
//        AppUser user = this.userService.getUser(loginRequest.getUsername());
//        RspLogin rspLogin = new RspLogin(loginRequest.getUsername(),user.getAuthority(), loginResponse.getAccessToken());
        return ResponseEntity.ok(rspLogin);
    }

    @Operation(summary = "解析Token")
    @GetMapping("/parser-token")
    public ResponseEntity<Map<String, Object>> parseToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String auth) {
        Map<String, Object> jwtPayload = this.jwtTokenService.parseToken(auth);
        return ResponseEntity.ok(jwtPayload);
    }
}