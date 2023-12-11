package com.school.learning.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RspBody {
    private String RspCode;
    private String RspMsg;
    private Object RspData;

}
