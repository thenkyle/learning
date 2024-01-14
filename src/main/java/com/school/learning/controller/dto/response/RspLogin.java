package com.school.learning.controller.dto.response;

import com.school.learning.constants.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RspLogin {
    private String account;
    private UserAuthority authority;
    private String token;
}
