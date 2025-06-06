package com.student.management.mapper;

import com.student.management.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {
    int insert(Student student);
    int updateById(Student student);
    int deleteById(String studentId);
    Student selectById(String studentId);
} 