package com.school.learning.controller;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.entity.StudentInfo;
import com.school.learning.service.StudentInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/student")
@Tag(name = "學員資料CRUD")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    //取得所有學生資料
    @GetMapping("/query")
    public List<StudentInfo> getStudentInfoList() {
        List<StudentInfo> res = studentInfoService.getStudentInfoList();

        if (res.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "查無資料");
        }
        // 不允許除以0，程式會出現錯誤(用來測試Log)
        // int x = 5 / 0;

        return res;
    }

    @GetMapping("/query/{studentId}")
    public StudentInfo getStudentInfoById(@PathVariable int studentId) {
        StudentInfo studentInfo = this.studentInfoService.getStudentInfoById(studentId);
        if(null == studentInfo){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "查無資料該學員資料");
        }
        return studentInfo;
    }

    //新增一筆學生資料
    @PostMapping("/insert")
    public RspBody insertStudentInfoReq(@RequestBody StudentReq studentReq) {
        boolean isInsert = studentInfoService.insertStudentInfo(studentReq);
        if (!isInsert) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "新增失敗.");
        }
        return new RspBody(HttpStatus.CREATED.value(), "新增成功.");
    }

    //刪除一筆學生資料
    @DeleteMapping("/delete/{studentId}")
    public RspBody deleteStudentInfoById(@PathVariable int studentId) {
        boolean isDelete = studentInfoService.deleteStudentById(studentId);
        if (!isDelete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "學員編號：" + studentId + " 資料不存在.");
        }
        return new RspBody(HttpStatus.OK.value(), "刪除成功.");
    }

    //修改指定學生資料
    @PutMapping("/edit/{studentId}")
    public RspBody putStudentInfoById(@PathVariable int studentId, @RequestBody StudentReq studentReq) {
        boolean isUpdate = studentInfoService.putStudentById(studentId, studentReq);
        if (!isUpdate) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "學員編號：" + studentId + " 資料不存在.");
        }
        return new RspBody(HttpStatus.OK.value(), "學員編號：" + studentId + " 資料更新成功.");
    }
}
