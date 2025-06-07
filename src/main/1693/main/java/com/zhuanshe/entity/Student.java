package com.zhuanshe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "student_id", unique = true, length = 20)
    private String studentId;

    @Column(name = "class_name", length = 50)
    private String className;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    public Student() {
    }

    public Student(Long userId, User user, String className, String studentId) {
        this.userId = userId;
        this.user = user;
        this.className = className;
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + userId +
                ", studentId='" + studentId + '\'' +
                ", className='" + className + '\'' +
                ", user=" + user +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}

