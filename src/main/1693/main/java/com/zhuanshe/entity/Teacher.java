package com.zhuanshe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "teacher_id", unique = true, length = 20)
    private String teacherId;

    @Column(name = "managed_class", length = 50)
    private String managedClass;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    public Teacher(Long userId, User user, String managedClass, String teacherId) {
        this.userId = userId;
        this.user = user;
        this.managedClass = managedClass;
        this.teacherId = teacherId;
    }

    public Teacher() {
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userId=" + userId +
                ", teacherId='" + teacherId + '\'' +
                ", managedClass='" + managedClass + '\'' +
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

    public String getManagedClass() {
        return managedClass;
    }

    public void setManagedClass(String managedClass) {
        this.managedClass = managedClass;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
}

