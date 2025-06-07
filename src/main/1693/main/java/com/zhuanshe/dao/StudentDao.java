package com.zhuanshe.dao;

import com.zhuanshe.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {
    // 根据用户ID查询学生信息
    Student findByUserId(Long userId);

}