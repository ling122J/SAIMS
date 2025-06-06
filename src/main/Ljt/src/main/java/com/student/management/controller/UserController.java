package com.student.management.controller;

import com.student.management.entity.User;
import com.student.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User result = userService.login(user.getUsername(), user.getPassword());
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body("用户名或密码错误");
        }
    }

    // 检查管理员权限
    @GetMapping("/isAdmin")
    public ResponseEntity<String> isAdmin(@RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        boolean isAdmin = userService.hasAdminPermission(user);
        return isAdmin ? ResponseEntity.ok("是管理员") : ResponseEntity.ok("不是管理员");
    }

    // 检查教师权限
    @GetMapping("/isTeacher")
    public ResponseEntity<String> isTeacher(@RequestParam String username) {
        User user = new User();
        user.setUsername(username);
        boolean isTeacher = userService.hasTeacherPermission(user);
        return isTeacher ? ResponseEntity.ok("是教师") : ResponseEntity.ok("不是教师");
    }
} 