package com.zhuanshe.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "admin_name", nullable = false, length = 50)
    private String adminName;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "permission_level")
    private Integer permissionLevel;

    // 与用户表建立一对一关联
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Admin() {
    }

    public Admin(Long adminId, String adminName, String department, 
                Integer permissionLevel, User user) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.department = department;
        this.permissionLevel = permissionLevel;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", department='" + department + '\'' +
                ", permissionLevel=" + permissionLevel +
                ", user=" + user +
                '}';
    }
}
