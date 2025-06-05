package com.example.grade.mapper;

import com.example.grade.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(@Param("username") String username);

    /**
     * 插入新用户
     * @param user 用户信息
     * @return 影响的行数
     */
    @Insert("INSERT INTO user(username, password, role, status, email) " +
            "VALUES(#{username}, #{password}, #{role}, #{status}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("SELECT * FROM user WHERE id = #{id}")
    User selectById(Long id);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User selectByUsername(String username);

    @Select("SELECT * FROM user WHERE status = 'PENDING'")
    List<User> selectPendingUsers();

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 影响的行数
     */
    @Update("UPDATE user SET password = #{password}, role = #{role}, " +
            "status = #{status}, email = #{email} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    int deleteById(Long id);
} 