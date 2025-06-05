package com.example.grade.mapper;

import com.example.grade.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    /**
     * 根据用户名和密码查询管理员
     * @param username 用户名
     * @param password 密码
     * @return 管理员信息
     */
    Admin findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 更新管理员密码
     * @param admin 管理员信息
     * @return 影响行数
     */
    int updatePassword(Admin admin);

    /**
     * 插入管理员信息
     * @param admin 管理员信息
     * @return 影响行数
     */
    int insert(Admin admin);
} 