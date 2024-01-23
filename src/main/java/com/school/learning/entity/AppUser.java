package com.school.learning.entity;

import com.school.learning.constants.UserAuthority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author kylelin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //PK
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private UserAuthority authority;
    @Column
    private boolean enabled = true;
    @Column
    private boolean premium = false;
    @Column
    private LocalDate trailExpiration = null;

    public static AppUser getTestAdminUser() {
        AppUser user = new AppUser();
        user.username = "admin";
        user.password = "admin";
        user.authority = UserAuthority.ADMIN;

        return user;
    }

    public static AppUser getTestUser() {
        AppUser user = new AppUser();
        user.username = "user1";
        user.password = "user1";
        user.authority = UserAuthority.USER;
        return user;
    }

    public static AppUser getTestGuestUser() {
        AppUser user = new AppUser();
        user.username = "test1";
        user.password = "test1";
        user.authority = UserAuthority.GUEST;
        user.enabled = false;
        user.trailExpiration = LocalDate.of(2022, 12, 31);

        return user;
    }
}
