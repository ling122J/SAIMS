package com.example.grade.controller;

import com.example.grade.entity.GpaWarning;
import com.example.grade.entity.Student;
import com.example.grade.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // 获取学生绩点报表
    @GetMapping("/gpa-report")
    public Map<String, Object> getGpaReport(@RequestParam String studentId) {
        return gradeService.generateGpaReport(studentId);
    }

    // 获取班级绩点报表
    @GetMapping("/class-gpa-report")
    public List<Map<String, Object>> getClassGpaReport(@RequestParam String className) {
        return gradeService.generateClassGpaReport(className);
    }

    // 获取当前预警阈值
    @GetMapping("/gpa-warning/current")
    public GpaWarning getCurrentGpaWarning() {
        return gradeService.getCurrentGpaWarning();
    }

    // 获取低于预警阈值的学生列表
    @GetMapping("/gpa-warning/students")
    public List<Student> getStudentsBelowGpaThreshold() {
        return gradeService.getStudentsBelowGpaThreshold();
    }

    // 设置预警阈值
    @PostMapping("/gpa-warning/threshold")
    public void setGpaWarningThreshold(@RequestParam Double threshold) {
        gradeService.setGpaWarningThreshold(threshold);
    }

    // 发送预警通知
    @PostMapping("/gpa-warning/send")
    public void sendGpaWarningNotifications() {
        gradeService.sendGpaWarningNotifications();
    }
} 