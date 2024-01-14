package com.school.learning;
import com.school.learning.constants.UserAuthority;
import com.school.learning.entity.UserInfo;
import com.school.learning.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (this.userInfoRepository.count() == 0) {
            // 表格是空的，進行初始化新增Admin、User帳號
            this.initData();
        } else {
            System.out.println("user_info 已經有資料，不需要再進行初始化.");
        }
    }

    private void initData() {
        // 初始化多個使用者
        List<UserInfo> users = Arrays.asList(
                createUser("admin", "admin", UserAuthority.ADMIN),
                createUser("user1", "password1", UserAuthority.USER)
        );
        // 保存多個使用者到資料庫
        this.userInfoRepository.saveAll(users);

        System.out.println("初始化成功,已新增多筆的User資料.");
    }

    private UserInfo createUser(String account, String password, UserAuthority authority) {
        UserInfo user = new UserInfo();
        user.setAccount(account);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setAuthority(authority);
        return user;
    }

}
