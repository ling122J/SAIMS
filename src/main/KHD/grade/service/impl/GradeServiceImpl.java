package com.example.grade.service.impl;

import com.example.grade.entity.Grade;
import com.example.grade.entity.GpaWarning;
import com.example.grade.entity.Student;
import com.example.grade.mapper.GradeMapper;
import com.example.grade.mapper.GpaWarningMapper;
import com.example.grade.mapper.StudentMapper;
import com.example.grade.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private GpaWarningMapper gpaWarningMapper;

    @Override
    @Transactional
    public void addGrade(Grade grade) {
        gradeMapper.insert(grade);
    }

    @Override
    @Transactional
    public void updateGrade(Grade grade) {
        gradeMapper.update(grade);
    }

    @Override
    @Transactional
    public void deleteGrade(Long id) {
        gradeMapper.deleteById(id);
    }

    @Override
    public List<Grade> getGradesByStudentId(String studentId) {
        return gradeMapper.selectByStudentId(studentId);
    }

    @Override
    public List<Grade> getGradesByStudentIdAndSemester(String studentId, String semester) {
        return gradeMapper.selectByStudentIdAndSemester(studentId, semester);
    }

    @Override
    public Double calculateGPA(String studentId) {
        return gradeMapper.calculateGPA(studentId);
    }

    @Override
    public Map<String, Object> generateGpaReport(String studentId) {
        Map<String, Object> report = new HashMap<>();
        Student student = studentMapper.selectByStudentId(studentId);
        List<Grade> grades = gradeMapper.selectByStudentId(studentId);
        
        report.put("studentInfo", student);
        report.put("grades", grades);
        report.put("gpa", calculateGPA(studentId));
        report.put("averageScore", gradeMapper.calculateAverageScore(studentId));
        
        return report;
    }

    @Override
    public List<Map<String, Object>> generateClassGpaReport(String className) {
        List<Student> students = studentMapper.selectAll();
        List<Map<String, Object>> report = new ArrayList<>();
        
        for (Student student : students) {
            if (student.getClassName().equals(className)) {
                Map<String, Object> studentReport = new HashMap<>();
                studentReport.put("studentInfo", student);
                studentReport.put("gpa", calculateGPA(student.getStudentId()));
                report.add(studentReport);
            }
        }
        
        return report;
    }

    @Override
    @Transactional
    public void setGpaWarningThreshold(Double threshold) {
        GpaWarning warning = new GpaWarning();
        warning.setWarningThreshold(threshold);
        warning.setIsActive(true);
        
        // 停用所有现有的预警设置
        gpaWarningMapper.deactivateAll();
        // 添加新的预警设置
        gpaWarningMapper.insert(warning);
    }

    @Override
    public GpaWarning getCurrentGpaWarning() {
        return gpaWarningMapper.selectActiveWarning();
    }

    @Override
    public List<Student> getStudentsBelowGpaThreshold() {
        GpaWarning warning = getCurrentGpaWarning();
        if (warning == null) {
            return new ArrayList<>();
        }
        
        List<Student> students = studentMapper.selectAll();
        List<Student> belowThreshold = new ArrayList<>();
        
        for (Student student : students) {
            Double gpa = calculateGPA(student.getStudentId());
            if (gpa != null && gpa < warning.getWarningThreshold()) {
                belowThreshold.add(student);
            }
        }
        
        return belowThreshold;
    }

    @Override
    public void sendGpaWarningNotifications() {
        List<Student> students = getStudentsBelowGpaThreshold();
        GpaWarning warning = getCurrentGpaWarning();
        
        if (warning == null || students.isEmpty()) {
            System.out.println("没有需要发送预警的学生");
            return;
        }

        for (Student student : students) {
            Double studentGpa = calculateGPA(student.getStudentId());
            String warningMessage = String.format(
                "绩点预警通知\n" +
                "学生姓名：%s\n" +
                "学号：%s\n" +
                "专业：%s\n" +
                "班级：%s\n" +
                "当前绩点：%.2f\n" +
                "预警阈值：%.2f\n" +
                "请及时与辅导员联系，制定学习改进计划。",
                student.getName(),
                student.getStudentId(),
                student.getMajor(),
                student.getClassName(),
                studentGpa,
                warning.getWarningThreshold()
            );

            // 这里可以集成邮件发送服务
            if (student.getEmail() != null && !student.getEmail().isEmpty()) {
                System.out.println("正在发送邮件到: " + student.getEmail());
                System.out.println("邮件内容:\n" + warningMessage);
            }

            // 这里可以集成短信发送服务
            if (student.getPhone() != null && !student.getPhone().isEmpty()) {
                System.out.println("正在发送短信到: " + student.getPhone());
                System.out.println("短信内容:\n" + warningMessage);
            }

            // 记录预警发送日志
            System.out.println(String.format(
                "已发送绩点预警通知 - 学生：%s（学号：%s），当前绩点：%.2f，预警阈值：%.2f",
                student.getName(),
                student.getStudentId(),
                studentGpa,
                warning.getWarningThreshold()
            ));
        }
    }

    @Override
    public List<Grade> getClassGpaStats(String className) {
        return gradeMapper.findByClassName(className);
    }

    @Override
    public List<Grade> getCourseGradeStats(String courseName) {
        return gradeMapper.findByCourseName(courseName);
    }

    @Override
    public List<Grade> getSemesterGradeStats(String semester) {
        return gradeMapper.findBySemester(semester);
    }

    @Override
    public Double calculateGpa(Long studentId) {
        return calculateGPA(studentId.toString());
    }

    // 计算成绩点数（4.0制）
    private double calculateGradePoints(Double score) {
        if (score >= 90) return 4.0;
        if (score >= 85) return 3.7;
        if (score >= 82) return 3.3;
        if (score >= 78) return 3.0;
        if (score >= 75) return 2.7;
        if (score >= 72) return 2.3;
        if (score >= 68) return 2.0;
        if (score >= 65) return 1.7;
        if (score >= 64) return 1.5;
        if (score >= 62) return 1.3;
        if (score >= 60) return 1.0;
        return 0.0;
    }
} 