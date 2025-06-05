package com.example.grade.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 绩点统计报表页面
    @GetMapping("/grade/gpa-report")
    public String gpaReportPage() {
        return "grade/gpa-report";
    }

    // 绩点预警设置页面
    @GetMapping("/grade/gpa-warning")
    public String gpaWarningPage() {
        return "grade/gpa-warning";
    }

    // 用户注册审核页面
    @GetMapping("/admin/user-review")
    public String userReviewPage() {
        return "admin/user-review";
    }

    // 操作日志审核页面
    @GetMapping("/admin/operation-log")
    public String operationLogPage() {
        return "admin/operation-log";
    }
} 