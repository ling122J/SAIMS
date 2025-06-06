package com.student.management.service;

import com.student.management.entity.Student;

public interface StudentService {
    boolean addStudent(Student student);
    boolean updateStudent(Student student);
    boolean deleteStudent(String studentId);
    Student getStudentById(String studentId);
} 