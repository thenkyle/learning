package com.school.learning.entity;

import com.school.learning.constants.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class AppUserDetails implements UserDetails {
    private AppUser appUser;

    public String getUsername() {
        return appUser.getUsername();
    }

    public String getPassword() {
        return appUser.getPassword();
    }

    public boolean isEnabled() {
        return appUser.isEnabled();
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(appUser.getAuthority());
    }

    public boolean isAccountNonExpired() {
        if (appUser.getTrailExpiration() == null) {
            return true;
        }
        return LocalDate.now().isBefore(appUser.getTrailExpiration());
    }

    // 自定義的 public 方法
    public Integer getId() { return appUser.getId(); }
    public UserAuthority getUserAuthority() { return appUser.getAuthority(); }
    public boolean isPremium() { return appUser.isPremium(); }
    public LocalDate getTrailExpiration() { return appUser.getTrailExpiration(); }

}
