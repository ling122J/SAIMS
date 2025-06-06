package com.student.management.service;

import com.student.management.entity.User;

public interface UserService {
    User login(String username, String password);
    boolean hasAdminPermission(User user);
    boolean hasTeacherPermission(User user);
} 