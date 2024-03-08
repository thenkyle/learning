package com.school.learning.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentReq {
    private String studentName;
    private String studentBth;
    private String studentGender;
    private String studentTel;

}