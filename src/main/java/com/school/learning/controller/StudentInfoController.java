package com.school.learning.controller;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.model.entity.StudentInfo;
import com.school.learning.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/learning/student")
@CrossOrigin
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    //取得所有學生資料
    @GetMapping("/query")
    public List<StudentInfo> getStudentInfoList(){
        List<StudentInfo> res = studentInfoService.getStudentInfoList();

        if(res.size() == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return res;
    }

    //新增一筆學生資料
    @PostMapping("/insert")
    public RspBody insertStudentInfoReq(@RequestBody StudentReq studentReq){
        RspBody rspBody = studentInfoService.insertStudentInfo(studentReq);
        return rspBody;
    }

    //刪除一筆學生資料
    @DeleteMapping("/delete/{studentId}")
    public RspBody deleteStudentInfoById(@PathVariable int studentId){
        RspBody rspBody = studentInfoService.deleteStudentById(studentId);
        return rspBody;
    }

    //修改指定學生資料
    @PutMapping("/edit/{studentId}")
    public RspBody putStudentInfoById(@PathVariable int studentId, @RequestBody StudentReq studentReq){
        RspBody rspBody = studentInfoService.putStudentById(studentId, studentReq);
        return rspBody;
    }
}
