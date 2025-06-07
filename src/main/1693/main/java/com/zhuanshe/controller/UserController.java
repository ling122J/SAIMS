package com.zhuanshe.controller;

import com.zhuanshe.entity.Scores;
import com.zhuanshe.entity.Student;
import com.zhuanshe.entity.User;
import com.zhuanshe.service.ScoreService;
import com.zhuanshe.service.StudentService;
import com.zhuanshe.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    // 添加登录接口
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, 
                              @RequestParam String password,
                              HttpServletRequest request) {
        try {
            User user = userService.login(username, password, request.getRemoteAddr());
            request.getSession().setAttribute("currentUser", user);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            // 明确返回错误信息格式
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "error", e.getMessage(),
                "timestamp", LocalDateTime.now()
            ));
        }
    }

    // 新增注册接口
    @PostMapping("/register")
    public String register(@RequestParam String username, 
                          @RequestParam String password,
                          @RequestParam String email) {
        return userService.register(username, password, email);
    }

    // 新增获取当前用户接口
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if(user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // 新增学生信息查询接口
    @GetMapping("/student/{userId}")
    public ResponseEntity<?> getStudentInfo(@PathVariable Long userId) {
        try {
            Student student = studentService.getStudentInfo(userId);
            if (student != null) {
                return ResponseEntity.ok(student);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("学生信息未找到");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询异常");
        }
    }

    // 学生成绩查询接口
    @PostMapping("/queryScores")
    public ResponseEntity<?> queryStudentScores(@RequestParam Integer studentId) {
        try {
            List<Scores> scores = scoreService.getStudentScores(studentId);
            return ResponseEntity.ok(scores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败");
        }
    }

    // 科目不合格查询接口
    @PostMapping("/queryFailStudents")
    public ResponseEntity<?> queryFailStudents(@RequestParam String courseName) {
        try {
            List<Student> students = scoreService.getFailStudents(courseName);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("查询失败");
        }
    }
}