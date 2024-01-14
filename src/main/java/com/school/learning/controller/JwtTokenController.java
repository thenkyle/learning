package com.school.learning.controller;

import com.school.learning.config.JwtTokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "取得JwtToken")
@RequestMapping("/jwt-token")
public class JwtTokenController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public String getJwtToken() {
        return this.jwtTokenProvider.generateToken("admin");
    }
}