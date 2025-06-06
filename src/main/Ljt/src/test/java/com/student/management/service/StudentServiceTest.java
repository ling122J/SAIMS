package com.student.management.service;

import com.student.management.entity.Student;
import com.student.management.mapper.StudentMapper;
import com.student.management.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {
    
    @Mock
    private StudentMapper studentMapper;
    
    @InjectMocks
    private StudentServiceImpl studentService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    //测试添加学生功能
    void testAddStudent() {
        Student student = new Student();
        student.setStudentId("2025001");
        student.setName("张三");
        student.setPhone("13812345678");
        
        when(studentMapper.insert(student)).thenReturn(1);
        
        boolean result = studentService.addStudent(student);
        assertTrue(result);
        verify(studentMapper, times(1)).insert(student);
    }
    
    @Test
    //测试更新学生信息功能
    void testUpdateStudent() {
        Student student = new Student();
        student.setStudentId("2024001");
        student.setName("张三");
        student.setPhone("13812345678");
        
        when(studentMapper.updateById(student)).thenReturn(1);
        
        boolean result = studentService.updateStudent(student);
        assertTrue(result);
        verify(studentMapper, times(1)).updateById(student);
    }
    
    @Test
    // 测试删除学生功能
    void testDeleteStudent() {
        String studentId = "2023001";
        
        when(studentMapper.deleteById(studentId)).thenReturn(1);
        
        boolean result = studentService.deleteStudent(studentId);
        assertTrue(result);
        verify(studentMapper, times(1)).deleteById(studentId);
    }
    
    @Test
    //测试获取学生信息时手机号码的脱敏处理
    void testGetStudentWithMaskedPhone() {
        Student student = new Student();
        student.setStudentId("2023001");
        student.setName("张三");
        student.setPhone("13812345678");
        
        when(studentMapper.selectById("2023001")).thenReturn(student);
        
        Student result = studentService.getStudentById("2023001");
        assertEquals("138****5678", result.getPhone());
    }
} 