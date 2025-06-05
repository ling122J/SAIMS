package com.example.grade.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GpaWarning {
    private Long id;
    private Double warningThreshold;
    private Boolean isActive;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String createdBy;
    private String updatedBy;
} 