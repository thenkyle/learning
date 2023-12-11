package com.school.learning.service;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.model.entity.StudentInfo;
import com.school.learning.repository.StudentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;

    //取得所有學生資料
    public List<StudentInfo> getStudentInfoList() {
        List<StudentInfo> res = studentInfoRepository.findAll();
        return res;
    }

    //新增學生資料
    public RspBody insertStudentInfo(StudentReq studentReq) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentName(studentReq.getStudentName());
        studentInfo.setStudentAge(studentReq.getStudentAge());
        studentInfo.setStudentGender(studentReq.getStudentGender());
        studentInfo.setStudentTel(studentReq.getStudentTel());
        studentInfoRepository.save((studentInfo));
        RspBody rspBody = new RspBody("200", "insert success!", studentInfo);
        return rspBody;
    }

    //刪除指定學生資料
    public RspBody deleteStudentById(int studentId) {
        RspBody rspBody = new RspBody();
        StudentInfo studentInfo = studentInfoRepository.findById(studentId);

        if (studentInfo == null) {
            rspBody.setRspCode("400");
            rspBody.setRspMsg("Delete Failed!");
            rspBody.setRspData("StudentId: " + studentId + " not found");
        } else {
            rspBody.setRspCode("200");
            rspBody.setRspMsg("Delete Success!");
            studentInfoRepository.deleteById(studentId);
        }

        return rspBody;
    }


    //修改指定學生資料
    public RspBody putStudentById(int studentId, StudentReq studentReq) {
        StudentInfo studentInfo = studentInfoRepository.findById(studentId);
        RspBody rspBody = new RspBody();

        if (null == studentInfo) {
            rspBody.setRspCode("400");
            rspBody.setRspMsg("Update Failed!");
            rspBody.setRspData("StudentId: " + studentId + " not found");
        } else {
            studentInfo.setStudentName(studentReq.getStudentName());
            studentInfo.setStudentAge(studentReq.getStudentAge());
            studentInfo.setStudentGender(studentReq.getStudentGender());
            studentInfo.setStudentTel(studentReq.getStudentTel());
            rspBody.setRspCode("200");
            rspBody.setRspMsg("Update Success!");
            rspBody.setRspData(studentInfo);
            studentInfoRepository.save(studentInfo);
        }
        return rspBody;
    }
}
