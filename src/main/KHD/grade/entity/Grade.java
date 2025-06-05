package com.example.grade.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Grade {
    private Long id;
    private String studentId;
    private String courseName;
    private Double score;
    private Double credit;
    private String semester;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 