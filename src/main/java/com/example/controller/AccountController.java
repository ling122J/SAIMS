package com.example.controller;

import com.example.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AccountController {
    @Resource
    AccountService accountService;

    @ResponseBody
    @PostMapping("/checkUsername")
    public Map<String, Object> checkUsername(@RequestParam String username) {
        return accountService.checkUsername(username);
    }

    @ResponseBody
    @PostMapping("/Through")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> params,HttpServletRequest request) throws Exception{
        return accountService.createUser(params,request);
    }

    @ResponseBody
    @PreAuthorize("hasRole('Admin')")
    @PostMapping("/user/list")
    public ResponseEntity<Map<String, Object>> getAllUsers(@RequestBody Map<String, Integer> params) {
        int page = params.getOrDefault("page", 1);
        int size = params.getOrDefault("size", 10);
        return accountService.getUsers(page, size);
    }
}
