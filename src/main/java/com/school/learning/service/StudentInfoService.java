package com.school.learning.service;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.controller.dto.response.RspBody;
import com.school.learning.entity.StudentInfo;
import com.school.learning.repository.StudentInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
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
    public boolean insertStudentInfo(StudentReq studentReq, MultipartFile file) {
        try {

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentName(studentReq.getStudentName());
            studentInfo.setStudentBth(studentReq.getStudentBth());
            studentInfo.setStudentGender(studentReq.getStudentGender());
            studentInfo.setStudentTel(studentReq.getStudentTel());

            // 如果有檔案再儲存
            if (file != null && !file.isEmpty()) {
                studentInfo.setFile(file.getBytes());
            }

            // 儲存到DB
            studentInfoRepository.save(studentInfo);

            // 返回 true 表示成功建立
            return true;
        } catch (IOException e) {
            // 处理异常，这里可以根据实际情况进行日志记录等操作
            e.printStackTrace();
            // 返回 false 表示插入失败
            return false;
        }
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
    public boolean putStudentById(int studentId, StudentReq studentReq, MultipartFile file) throws IOException {

        StudentInfo studentInfo = studentInfoRepository.findById(studentId);

        if (null == studentInfo) {
            return false;
        }

        studentInfo.setStudentName(studentReq.getStudentName());
        studentInfo.setStudentBth(studentReq.getStudentBth());
        studentInfo.setStudentGender(studentReq.getStudentGender());
        studentInfo.setStudentTel(studentReq.getStudentTel());
        if (file != null && !file.isEmpty()) {
            studentInfo.setFile(file.getBytes());
        }
        studentInfoRepository.save(studentInfo);
        return true;
    }
}
