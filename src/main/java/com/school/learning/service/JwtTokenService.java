package com.school.learning.service;

import com.school.learning.controller.dto.request.LoginReq;
import com.school.learning.controller.dto.response.RspLogin;
import com.school.learning.entity.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService {
    private Key secretKey;
    private JwtParser jwtParser;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostConstruct
    private void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    /**建立token*/
    public RspLogin createToken(LoginReq request) {
        // 封裝帳密
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        // 執行帳密認證
        authToken = this.authenticationProvider.authenticate(authToken);
        // 以自定義的 UserDetails 接收已認證的使用者
        AppUserDetails userDetails = (AppUserDetails) authToken.getPrincipal();
        // 產生 token
        String accessToken = createAccessToken(userDetails.getUsername());
        RspLogin res = new RspLogin();
        res.setToken(accessToken);
        res.setUsername(userDetails.getUsername());
        res.setAuthority(userDetails.getUserAuthority());
        return res;
    }

    /**建立JwtToken細節設定*/
    private String createAccessToken(String username) {
        // 有效時間（毫秒）
        long expirationMillis = Instant.now()
                .plusSeconds(90)
                .getEpochSecond()
                * 1000;

        // 設置標準內容與自定義內容
        Claims claims = Jwts.claims();
        claims.setSubject("Access Token");
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(expirationMillis));
        claims.put("username", username);

        // 簽名後產生 token
        return Jwts.builder()
                .setClaims(claims)
                .signWith(this.secretKey)
                .compact();
    }

    /**解析token*/
    public Map<String, Object> parseToken(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return new HashMap<>(claims);
    }

    /**拆解 prefix Bearer*/
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
