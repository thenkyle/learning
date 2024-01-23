package com.school.learning.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //啟用Spring Security的Web安全性。
public class SecurityConfig {

    private static final String[] SWAGGER_AUTH_WHITELIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v3/api-docs/**"
    };
    private static final String[] AUTH_WHITELIST = {
            "/auth/login",
            "/api/v1/students/**"
//            "/api/v1/users/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http
//            ,JwtTokenFilter jwtTokenFilter
    ) throws Exception {
        //TODO
        http.authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests
                            .requestMatchers(SWAGGER_AUTH_WHITELIST).permitAll() //允許swagger不用身份授權的白名單.
                            .requestMatchers(AUTH_WHITELIST).permitAll() //允許不用做身份授權的白名單.
                            .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll() //允許建立使用者
                            .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("ADMIN") //ADMIN才可查詢全部使用者
                            .requestMatchers(HttpMethod.GET,"/api/v1/users/?*").hasAnyAuthority("ADMIN","USER") //ADMIN和USER可以查詢指定使用者                            .requestMatchers(HttpMethod.GET, "/api/v1/students/**").permitAll()
//                            .requestMatchers((HttpMethod.GET, "/api/v1/students").permitAll()
//                            .requestMatchers(HttpMethod.POST, "/api/v1/students").permitAll()
//                            .requestMatchers(HttpMethod.PUT, "/api/v1/students/?*").permitAll()
//                            .requestMatchers(HttpMethod.DELETE, "/api/v1/students/?*").permitAll()
                            .anyRequest().authenticated(); //上述以外的Url都要授權.
                })
                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }

    /**
     * 密碼加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**會將輸入的明碼去跟DB做解碼驗證，若需要複雜如：帳號鎖定、依照權限分配能取得的DB資源等等*/
    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
