package com.example.grade.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Student {
    private Long id;
    private String studentId;
    private String name;
    private String major;
    private String className;
    private String email;
    private String phone;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String grade;
    private String admissionYear;
} 