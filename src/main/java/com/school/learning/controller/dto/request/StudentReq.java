package com.school.learning.controller.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentReq {
    private String studentName;
    private String studentBth;
    private String studentGender;
    private String studentTel;
    private MultipartFile file;
}