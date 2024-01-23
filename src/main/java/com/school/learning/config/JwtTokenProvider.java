package com.school.learning.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenProvider {

    /**
     * jwt的密鑰
     */
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    /**
     * 存活時間
     */
    @Value("${jwt.expirationMs}")
    private long jwtExpirationInMs;

    private JwtParser jwtParser;

    @PostConstruct
    private void init() {
        this.jwtParser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
    }

    /**
     * 建立Jwt Token
     */
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryTime = new Date(now.getTime() + jwtExpirationInMs);

        // 設置標準內容與自定義內容
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.setIssuedAt(now);
        claims.setExpiration(expiryTime);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 驗證Jwt Token
     */
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(this.extractTokenFromHeader(token));
            return true;
        } catch (Exception ex) {
            // JWT 簽名驗證失敗或過期等
            return false;
        }
    }

    public Map<String, Object> parseToken(String token) {
        System.out.println(token);
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return new HashMap<>(claims);
    }

    private String extractTokenFromHeader(String header) {
        // 假設 Authorization 標頭的格式是 "Bearer <token>"
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7); // 去除 "Bearer " 部分，得到實際的 JWT token
        }
        return null;
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                getUsernameFromToken(token),
                "",
                new ArrayList<>());

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }


}
