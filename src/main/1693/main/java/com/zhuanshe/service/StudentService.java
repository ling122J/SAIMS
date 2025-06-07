package com.zhuanshe.service;

import com.zhuanshe.dao.StudentDao;
import com.zhuanshe.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    
    @Autowired
    private StudentDao studentDao;

    // 根据学生用户ID查询详细信息
    public Student getStudentInfo(Long userId) {
        return studentDao.findByUserId(userId);
    }
}