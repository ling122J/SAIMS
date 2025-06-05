package com.example.grade.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String role;
    private String status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String lastLoginIp;
    private LocalDateTime lastLoginTime;
} 