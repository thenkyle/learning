package com.school.learning.constants;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    ADMIN, USER, GUEST;

    @Override
    public String getAuthority(){
        return name();
    }
}