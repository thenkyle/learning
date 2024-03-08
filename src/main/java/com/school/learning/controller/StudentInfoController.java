package com.school.learning.controller;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.entity.StudentInfo;
import com.school.learning.service.StudentInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@Tag(name = "學員資訊")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    @Operation(summary = "查詢所有學生資料")
    @GetMapping
    public List<StudentInfo> getStudentInfoList() {
        List<StudentInfo> res = studentInfoService.getStudentInfoList();

        if (res.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "查無資料");
        }
        // 不允許除以0，程式會出現錯誤(用來測試Log)
        // int x = 5 / 0;

        return res;
    }

    @Operation(summary = "查詢指定學生資料")
    @GetMapping("/{studentId}")
    public StudentInfo getStudentInfoById(@PathVariable int studentId) {
        StudentInfo studentInfo = this.studentInfoService.getStudentInfoById(studentId);
        if(null == studentInfo){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "查無資料該學員資料");
        }
        return studentInfo;
    }

    @Operation(summary = "新增一筆學生資料")
    @PostMapping
    public RspBody insertStudentInfoReq(@RequestBody StudentReq studentReq) {
        boolean isInsert = studentInfoService.insertStudentInfo(studentReq);
        if (!isInsert) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "新增失敗.");
        }
        return new RspBody(HttpStatus.CREATED.value(), "新增成功.");
    }

    @Operation(summary = "刪除一筆學生資料")
    @DeleteMapping("/{studentId}")
    public RspBody deleteStudentInfoById(@PathVariable int studentId) {
        boolean isDelete = studentInfoService.deleteStudentById(studentId);
        if (!isDelete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "學員編號：" + studentId + " 資料不存在.");
        }
        return new RspBody(HttpStatus.OK.value(), "刪除成功.");
    }

    @Operation(summary = "修改指定學生資料")
    @PutMapping("/{studentId}")
    public RspBody putStudentInfoById(@PathVariable int studentId, @RequestBody StudentReq studentReq) {
        boolean isUpdate = studentInfoService.putStudentById(studentId, studentReq);

        if (!isUpdate) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "學員編號：" + studentId + " 資料不存在.");
        }
        return new RspBody(HttpStatus.OK.value(), "學員編號：" + studentId + " 資料更新成功.");
    }
}
