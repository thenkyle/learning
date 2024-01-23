package com.school.learning.controller.dto.request;

import com.school.learning.constants.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserReq {
    private String username;
    private String password;
    private UserAuthority authority;
}
