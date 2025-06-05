package com.example.grade.service;

import com.example.grade.entity.Admin;
import com.example.grade.entity.OperationLog;
import com.example.grade.entity.User;

import java.util.Date;
import java.util.List;

public interface AdminService {
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 管理员信息
     */
    Admin login(String username, String password);

    /**
     * 更新管理员密码
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否更新成功
     */
    boolean updatePassword(String username, String oldPassword, String newPassword);

    void addAdmin(Admin admin);

    // 用户注册审核
    List<User> getPendingUsers();
    void approveUser(Long userId);
    void rejectUser(Long userId);
    
    // 操作日志审核
    List<OperationLog> getAllOperationLogs();
    List<OperationLog> getOperationLogsByType(String operationType);
    List<OperationLog> getOperationLogsByUserId(Long userId);
    List<OperationLog> getOperationLogs(String operationType, Long userId, Date startTime, Date endTime);
    void analyzeOperationLogs();
} 