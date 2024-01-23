package com.school.learning;

import com.school.learning.config.SecurityConfig;
import com.school.learning.constants.UserAuthority;
import com.school.learning.entity.AppUser;
import com.school.learning.repository.UserRepository;
import com.school.learning.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public void run(String... args) throws Exception {
        List<AppUser> users = this.userService.getUsers();
        if (users.size() == 0) {
            // 表格是空的，進行初始化新增Admin、User帳號
            this.initData();
        } else {
            System.out.println("資料庫已有User資料，無需初始化.");
        }
    }

    private void initData() {
        var adminUser = AppUser.getTestAdminUser();
        var normalUser = AppUser.getTestUser();
        var guestUser = AppUser.getTestGuestUser();

        Stream.of(adminUser, normalUser, guestUser)
                .forEach(u -> {
                    var encodedPwd = this.securityConfig.passwordEncoder().encode(u.getPassword());
                    u.setPassword(encodedPwd);
                });

        // 初始化多個使用者
        List<AppUser> users = Arrays.asList(adminUser, normalUser, guestUser);
        System.out.println("users:" + users);
        // 保存多個使用者到資料庫
        this.userRepository.saveAll(users);
        System.out.println("初始化成功,已新增多筆的User資料.");
    }


}
