package com.school.learning.controller.dto.response;

import com.school.learning.constants.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RspLogin {
    private String username;
    private UserAuthority authority;
    private String token;
}
