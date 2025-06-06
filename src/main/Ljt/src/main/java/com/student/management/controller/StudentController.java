package com.student.management.controller;

import com.student.management.entity.Student;
import com.student.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // 添加学生
    @PostMapping("/add")
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        boolean result = studentService.addStudent(student);
        return result ? ResponseEntity.ok("添加成功") : ResponseEntity.badRequest().body("添加失败");
    }

    // 编辑学生
    @PostMapping("/update")
    public ResponseEntity<String> updateStudent(@RequestBody Student student) {
        boolean result = studentService.updateStudent(student);
        return result ? ResponseEntity.ok("编辑成功") : ResponseEntity.badRequest().body("编辑失败");
    }

    // 删除学生
    @PostMapping("/delete")
    public ResponseEntity<String> deleteStudent(@RequestBody Student student) {
        boolean result = studentService.deleteStudent(student.getStudentId());
        return result ? ResponseEntity.ok("删除成功") : ResponseEntity.badRequest().body("删除失败");
    }
} 