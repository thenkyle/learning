package com.school.learning;

import com.school.learning.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

//@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        //取得header內的token.
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (null != bearerToken) {
            String accessToken = bearerToken.replace("Bearer", "").trim();
            //解析token
            Map<String, Object> tokenPayload = this.jwtTokenService.parseToken(accessToken);
            String username = (String) tokenPayload.get("username");
            //查詢使用者
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            //將使用者身份和權限傳給Spring Security
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //將Request送給Controller或下一個Filter
            filterChain.doFilter(request, response);
        }
    }
}
