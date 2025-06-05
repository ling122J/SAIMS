package com.example.grade.entity;

import lombok.Data;

@Data
public class Admin {
    private Integer id;
    private String username;
    private String password;
    private String realName;
    private String email;
    private String phone;
    private String role;
    private String status;
    private String createTime;
    private String updateTime;
} 