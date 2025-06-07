package com.zhuanshe.dao;

import com.zhuanshe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    // 通过用户名查找用户
    User findByUsername(String username);

    // 更新用户密码
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = ?2 WHERE u.username = ?1")
    void updatePassword(String username, String newPassword);
}