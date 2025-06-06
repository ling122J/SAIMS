package com.student.management.service;

import com.student.management.entity.User;
import com.student.management.mapper.UserMapper;
import com.student.management.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    //测试管理员登录功能
    void testLoginAsAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole("ADMIN");
        
        when(userMapper.findByUsername("admin")).thenReturn(admin);
        
        User result = userService.login("admin", "admin123");
        assertNotNull(result);
        assertEquals("ADMIN", result.getRole());
    }
    
    @Test
    //测试教师登录功能
    void testLoginAsTeacher() {
        User teacher = new User();
        teacher.setUsername("teacher");
        teacher.setPassword("teacher123");
        teacher.setRole("TEACHER");
        
        when(userMapper.findByUsername("teacher")).thenReturn(teacher);
        
        User result = userService.login("teacher", "teacher123");
        assertNotNull(result);
        assertEquals("TEACHER", result.getRole());
    }
    
    @Test
    //测试管理员权限检查
    void testCheckAdminPermission() {
        User admin = new User();
        admin.setRole("ADMIN");
        
        boolean hasPermission = userService.hasAdminPermission(admin);
        assertTrue(hasPermission);
    }
    
    @Test
    //测试教师权限检查
    void testCheckTeacherPermission() {
        User teacher = new User();
        teacher.setRole("TEACHER");
        
        boolean hasPermission = userService.hasTeacherPermission(teacher);
        assertTrue(hasPermission);
    }
} 