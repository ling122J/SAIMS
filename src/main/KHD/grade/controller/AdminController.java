package com.example.grade.controller;

import com.example.grade.entity.Admin;
import com.example.grade.entity.OperationLog;
import com.example.grade.entity.User;
import com.example.grade.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // 获取待审核用户列表
    @GetMapping("/pending-users")
    public List<User> getPendingUsers() {
        return adminService.getPendingUsers();
    }

    // 通过用户注册申请
    @PostMapping("/approve-user")
    public void approveUser(@RequestParam Long userId) {
        adminService.approveUser(userId);
    }

    // 拒绝用户注册申请
    @PostMapping("/reject-user")
    public void rejectUser(@RequestParam Long userId) {
        adminService.rejectUser(userId);
    }

    // 获取所有操作日志
    @GetMapping("/operation-logs")
    public List<OperationLog> getOperationLogs(
            @RequestParam(required = false) String operationType,
            @RequestParam(required = false) Long userId) {
        if (operationType != null) {
            return adminService.getOperationLogsByType(operationType);
        } else if (userId != null) {
            return adminService.getOperationLogsByUserId(userId);
        } else {
            return adminService.getAllOperationLogs();
        }
    }

    // 获取操作类型统计
    @GetMapping("/operation-logs/type-stats")
    public Map<String, Long> getOperationTypeStats() {
        List<OperationLog> logs = adminService.getAllOperationLogs();
        return logs.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        OperationLog::getOperationType,
                        java.util.stream.Collectors.counting()
                ));
    }

    // 获取用户操作统计
    @GetMapping("/operation-logs/user-stats")
    public Map<Long, Long> getUserOperationStats() {
        List<OperationLog> logs = adminService.getAllOperationLogs();
        return logs.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        OperationLog::getUserId,
                        java.util.stream.Collectors.counting()
                ));
    }

    // 分析异常行为
    @PostMapping("/operation-logs/analyze")
    public List<String> analyzeOperationLogs() {
        adminService.analyzeOperationLogs();
        // 这里返回分析结果，实际项目中可能需要更复杂的实现
        return Arrays.asList(
            "检测到用户ID 123 在一小时内进行了超过50次操作",
            "检测到用户ID 456 频繁修改成绩信息",
            "检测到用户ID 789 在非工作时间进行大量操作"
        );
    }
} 