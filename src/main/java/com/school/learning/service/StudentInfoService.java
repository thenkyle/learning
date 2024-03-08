package com.school.learning.service;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.entity.StudentInfo;
import com.school.learning.repository.StudentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentInfoService {
    @Autowired
    private StudentInfoRepository studentInfoRepository;

    //取得所有學生資料
    public List<StudentInfo> getStudentInfoList() {
        List<StudentInfo> res = this.studentInfoRepository.findAll();
        return res;
    }

    //取得指定學生資料
    public StudentInfo getStudentInfoById(int studentId){
        StudentInfo studentInfo = this.studentInfoRepository.findById(studentId);
        return studentInfo;
    }

    //新增學生資料
    public boolean insertStudentInfo(StudentReq studentReq) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentName(studentReq.getStudentName());
        studentInfo.setStudentBth(studentReq.getStudentBth());
        studentInfo.setStudentGender(studentReq.getStudentGender());
        studentInfo.setStudentTel(studentReq.getStudentTel());
        this.studentInfoRepository.save(studentInfo);
        return true;
    }

    //刪除指定學生資料
    public boolean deleteStudentById(int studentId) {
        StudentInfo studentInfo = this.studentInfoRepository.findById(studentId);
        if (null == studentInfo) {
            return false;
        }
        this.studentInfoRepository.deleteById(studentId);
        return true;
    }


    //修改指定學生資料
    public boolean putStudentById(int studentId, StudentReq studentReq) {
        StudentInfo studentInfo = studentInfoRepository.findById(studentId);
        if (null == studentInfo) {
            return false;
        }
        studentInfo.setStudentName(studentReq.getStudentName());
        studentInfo.setStudentBth(studentReq.getStudentBth());
        studentInfo.setStudentGender(studentReq.getStudentGender());
        studentInfo.setStudentTel(studentReq.getStudentTel());
        studentInfoRepository.save(studentInfo);
        return true;
    }
}
