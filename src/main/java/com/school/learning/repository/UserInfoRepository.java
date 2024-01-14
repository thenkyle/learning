package com.school.learning.repository;
import com.school.learning.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    // 根據使用者帳號查詢使用者信息
    // 傳統方
    // UserInfo findByAccount(String account);
     Optional<UserInfo> findByAccount(String account);
}

