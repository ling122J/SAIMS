package com.student.management.service.impl;

import com.student.management.entity.User;
import com.student.management.mapper.UserMapper;
import com.student.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User login(String username, String password) {
        // 根据用户名查询用户
        User user = userMapper.findByUsername(username);
        // 验证密码是否正确
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    @Override
    // 检查用户是否具有管理员权限
    public boolean hasAdminPermission(User user) {
        return user != null && "ADMIN".equals(user.getRole());
    }
    
    @Override
    // 检查用户是否具有教师权限
    public boolean hasTeacherPermission(User user) {
        return user != null && "TEACHER".equals(user.getRole());
    }
} 