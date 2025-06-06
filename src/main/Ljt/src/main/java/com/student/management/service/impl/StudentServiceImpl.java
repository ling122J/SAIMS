package com.student.management.service.impl;

import com.student.management.entity.Student;
import com.student.management.mapper.StudentMapper;
import com.student.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Override
    // 调用mapper的insert方法，返回影响的行数
    // 如果大于0表示添加成功
    public boolean addStudent(Student student) {
        return studentMapper.insert(student) > 0;
    }
    
    @Override
    // 调用mapper的updateById方法，返回影响的行数
    // 如果大于0表示更新成功
    public boolean updateStudent(Student student) {
        return studentMapper.updateById(student) > 0;
    }
    
    @Override
    // 调用mapper的deleteById方法，返回影响的行数
    // 如果大于0表示删除成功
    public boolean deleteStudent(String studentId) {
        return studentMapper.deleteById(studentId) > 0;
    }
    
    @Override
    // 调用mapper的selectById方法查询学生信息
    public Student getStudentById(String studentId) {
        Student student = studentMapper.selectById(studentId);
        if (student != null && student.getPhone() != null) {
            // 手机号脱敏处理
            String phone = student.getPhone();
            if (phone.length() >= 11) {
                student.setPhone(phone.substring(0, 3) + "****" + phone.substring(7));
            }
        }
        return student;
    }
} 