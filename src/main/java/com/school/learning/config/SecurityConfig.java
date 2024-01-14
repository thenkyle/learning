package com.school.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity //啟用Spring Security的Web安全性。
@Configuration
public class SecurityConfig {
//    @Autowired
//    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO
        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**","/jwt-token").permitAll() //允許swagger頁面不用身份授權.
                    .requestMatchers( "/api/v1/users/login").permitAll() //允許login呼叫不用身份授權,但登入會做密碼驗證.
                    .requestMatchers(HttpMethod.GET, "/**").permitAll() //允許所有GET的呼叫不用身份授權
                    .requestMatchers(HttpMethod.POST, "/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE, "/**").permitAll() //允許所有POST的呼叫不用身份授權
                    .anyRequest().authenticated(); //上述以外的Url都要授權.
        }).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /**密碼加密*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
