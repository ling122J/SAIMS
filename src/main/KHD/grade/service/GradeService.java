package com.example.grade.service;

import com.example.grade.entity.Grade;
import com.example.grade.entity.GpaWarning;
import com.example.grade.entity.Student;

import java.util.List;
import java.util.Map;

public interface GradeService {
    // 成绩管理
    void addGrade(Grade grade);
    void updateGrade(Grade grade);
    void deleteGrade(Long id);
    List<Grade> getGradesByStudentId(String studentId);
    List<Grade> getGradesByStudentIdAndSemester(String studentId, String semester);
    
    // 绩点计算与统计
    Double calculateGPA(String studentId);
    Double calculateGpa(Long studentId);
    Map<String, Object> generateGpaReport(String studentId);
    List<Map<String, Object>> generateClassGpaReport(String className);
    
    // 绩点预警
    void setGpaWarningThreshold(Double threshold);
    GpaWarning getCurrentGpaWarning();
    List<Student> getStudentsBelowGpaThreshold();
    void sendGpaWarningNotifications();

    // 获取班级GPA统计
    List<Grade> getClassGpaStats(String className);

    // 获取课程成绩统计
    List<Grade> getCourseGradeStats(String courseName);

    // 获取学期成绩统计
    List<Grade> getSemesterGradeStats(String semester);
} 