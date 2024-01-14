//package com.school.learning.config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain
//    ) throws ServletException, IOException {
//        // 從請求中提取 JWT Token
//        String token = extractToken(request);
//
//        if (token != null && jwtTokenProvider.validateToken(token)) {
//            // 如果 Token 有效，將其放入安全上下文中
//            // 注意：實際情況可能需要更複雜的邏輯，例如從 Token 中解析用戶信息並設置安全上下文
//            // SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        // 繼續處理請求
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractToken(HttpServletRequest request) {
//        // 這裡實現從請求中提取 Token 的邏輯，例如從 Authorization 標頭中提取 Bearer Token
//        // 如果 Token 不在標頭中，可以在此處添加其他邏輯，例如從請求參數中提取
//        // 注意：實際情況可能需要更複雜的邏輯，根據你的需求來實現
//        return null;
//    }
//}
