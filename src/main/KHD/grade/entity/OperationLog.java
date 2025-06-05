package com.example.grade.entity;

import lombok.Data;
import java.util.Date;

@Data
public class OperationLog {
    private Long id;
    private Long userId;
    private String operationType;
    private String operationContent;
    private Date createTime;
    private String ipAddress;
    private String status;
} 