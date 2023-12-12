package com.school.learning.controller;

import com.school.learning.controller.dto.request.StudentReq;
import com.school.learning.model.entity.StudentInfo;
import com.school.learning.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/learning/student")
public class StudentInfoController {
    @Autowired
    private StudentInfoService studentInfoService;

    //取得所有學生資料
    @GetMapping
    public String getStudentInfoList(Model model){
        List<StudentInfo> res = studentInfoService.getStudentInfoList();
        model.addAttribute("students", res);
        return "studentInfo";
    }

    //Info -> Insert
    @GetMapping("/insert")
    public String showInsertForm(Model model) {
        model.addAttribute("students", new StudentInfo());
        return "studentInsert";
    }

    //新增一筆學生資料
    @PostMapping("/insert")
    public String insertStudentInfoReq(@ModelAttribute StudentReq studentReq){
        studentInfoService.insertStudentInfo(studentReq);
        return "redirect:/api/learning/student";
    }

    //刪除一筆學生資料
    @GetMapping("/delete/{studentId}")
    public String deleteStudentInfoById(@PathVariable int studentId, RedirectAttributes redirectAttributes) {
        studentInfoService.deleteStudentById(studentId);
        return "redirect:/api/learning/student";
    }

    //Info -> Edit
    @GetMapping("/edit/{studentId}")
    public String showEditForm(@PathVariable int studentId, Model model) {
        StudentInfo studentInfo = studentInfoService.getStudentInfoById(studentId);
        model.addAttribute("student", studentInfo);
        return "studentEdit";
    }

    //修改指定學生資料
    @PostMapping("/edit/{studentId}")
    public String putStudentInfoById(@PathVariable int studentId, @ModelAttribute StudentReq studentReq, Model model){
        studentInfoService.putStudentById(studentId, studentReq);
        return "redirect:/api/learning/student";
    }
}
