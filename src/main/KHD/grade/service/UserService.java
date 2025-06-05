package com.example.grade.service;

import com.example.grade.entity.User;

public interface UserService {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户对象，登录失败返回null
     */
    User login(String username, String password);

    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册成功的用户对象
     */
    User register(User user);

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象，不存在返回null
     */
    User findByUsername(String username);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新后的用户对象
     */
    User update(User user);
} 