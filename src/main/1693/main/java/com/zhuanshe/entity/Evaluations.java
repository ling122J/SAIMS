package com.zhuanshe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evaluations")
public class Evaluations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eval_id")
    private Long evalId;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "user_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AuditStatus status;

    @Column(name = "audit_time")
    private LocalDateTime auditTime;

    @ManyToOne
    @JoinColumn(name = "auditor_id", referencedColumnName = "user_id")
    private Admin auditor; // 需要Admin实体类支持

    // 枚举类型定义
    public enum AuditStatus {
        PENDING, APPROVED, REJECTED
    }

    // 构造方法
    public Evaluations() {}

    public Evaluations(Long evalId, Teacher teacher, Student student, String content, 
                      AuditStatus status, LocalDateTime auditTime, Admin auditor) {
        this.evalId = evalId;
        this.teacher = teacher;
        this.student = student;
        this.content = content;
        this.status = status;
        this.auditTime = auditTime;
        this.auditor = auditor;
    }

    // Getter和Setter方法
    public Long getEvalId() { return evalId; }
    public void setEvalId(Long evalId) { this.evalId = evalId; }

    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public AuditStatus getStatus() { return status; }
    public void setStatus(AuditStatus status) { this.status = status; }

    public LocalDateTime getAuditTime() { return auditTime; }
    public void setAuditTime(LocalDateTime auditTime) { this.auditTime = auditTime; }

    public Admin getAuditor() { return auditor; }
    public void setAuditor(Admin auditor) { this.auditor = auditor; }

    @Override
    public String toString() {
        return "Evaluations{" +
                "evalId=" + evalId +
                ", teacher=" + teacher +
                ", student=" + student +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", auditTime=" + auditTime +
                ", auditor=" + auditor +
                '}';
    }
}