package com.example.grade.service.impl;

import com.example.grade.entity.Admin;
import com.example.grade.entity.OperationLog;
import com.example.grade.entity.User;
import com.example.grade.mapper.AdminMapper;
import com.example.grade.mapper.OperationLogMapper;
import com.example.grade.mapper.UserMapper;
import com.example.grade.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<User> getPendingUsers() {
        return userMapper.selectPendingUsers();
    }

    @Override
    @Transactional
    public void approveUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null && "PENDING".equals(user.getStatus())) {
            user.setStatus("APPROVED");
            userMapper.update(user);
            
            // 记录操作日志
            OperationLog log = new OperationLog();
            log.setUserId(userId);
            log.setOperationType("USER_APPROVAL");
            log.setOperationContent("管理员批准了用户注册申请");
            operationLogMapper.insert(log);
        }
    }

    @Override
    @Transactional
    public void rejectUser(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null && "PENDING".equals(user.getStatus())) {
            user.setStatus("REJECTED");
            userMapper.update(user);
            
            // 记录操作日志
            OperationLog log = new OperationLog();
            log.setUserId(userId);
            log.setOperationType("USER_REJECTION");
            log.setOperationContent("管理员拒绝了用户注册申请");
            operationLogMapper.insert(log);
        }
    }

    @Override
    public List<OperationLog> getAllOperationLogs() {
        return operationLogMapper.selectAll();
    }

    @Override
    public List<OperationLog> getOperationLogsByType(String operationType) {
        return operationLogMapper.selectByOperationType(operationType);
    }

    @Override
    public List<OperationLog> getOperationLogsByUserId(Long userId) {
        return operationLogMapper.selectByUserId(userId);
    }

    @Override
    public void analyzeOperationLogs() {
        List<OperationLog> logs = operationLogMapper.selectAll();
        
        // 按操作类型分组统计
        Map<String, Long> operationCounts = logs.stream()
                .collect(Collectors.groupingBy(OperationLog::getOperationType, Collectors.counting()));
        
        // 按用户分组统计
        Map<Long, Long> userOperationCounts = logs.stream()
                .collect(Collectors.groupingBy(OperationLog::getUserId, Collectors.counting()));
        
        // 输出分析结果
        System.out.println("操作日志分析结果：");
        System.out.println("1. 操作类型统计：");
        operationCounts.forEach((type, count) -> 
            System.out.println("   - " + type + ": " + count + "次"));
        
        System.out.println("2. 用户操作统计：");
        userOperationCounts.forEach((userId, count) -> 
            System.out.println("   - 用户ID " + userId + ": " + count + "次操作"));
        
        // 检测异常行为（示例：同一用户短时间内大量操作）
        Date oneHourAgo = new Date(System.currentTimeMillis() - 3600000);
        Map<Long, Long> recentUserOperations = logs.stream()
                .filter(log -> log.getCreateTime().after(oneHourAgo))
                .collect(Collectors.groupingBy(OperationLog::getUserId, Collectors.counting()));
        
        System.out.println("3. 最近一小时内异常操作检测：");
        recentUserOperations.forEach((userId, count) -> {
            if (count > 50) { // 假设一小时50次操作为异常阈值
                System.out.println("   - 警告：用户ID " + userId + " 在一小时内进行了 " + count + " 次操作，可能存在异常行为");
            }
        });
    }

    @Override
    public Admin login(String username, String password) {
        return adminMapper.findByUsernameAndPassword(username, password);
    }

    @Override
    @Transactional
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        Admin admin = adminMapper.findByUsernameAndPassword(username, oldPassword);
        if (admin != null) {
            admin.setPassword(newPassword);
            return adminMapper.updatePassword(admin) > 0;
        }
        return false;
    }

    @Override
    @Transactional
    public void addAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<OperationLog> getOperationLogs(String operationType, Long userId, Date startTime, Date endTime) {
        List<OperationLog> logs = operationLogMapper.selectAll();
        return logs.stream()
                .filter(log -> operationType == null || operationType.equals(log.getOperationType()))
                .filter(log -> userId == null || userId.equals(log.getUserId()))
                .filter(log -> startTime == null || log.getCreateTime().after(startTime))
                .filter(log -> endTime == null || log.getCreateTime().before(endTime))
                .collect(Collectors.toList());
    }
} 