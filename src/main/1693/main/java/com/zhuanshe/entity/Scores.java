package com.zhuanshe.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "scores")
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id")
    private Student student;

    @Column(name = "course_name", length = 50)
    private String courseName;

    @Column(name = "score_value", precision = 5)
    private Double scoreValue;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Override
    public String toString() {
        return "Scores{" +
                "scoreId=" + scoreId +
                ", student=" + student +
                ", courseName='" + courseName + '\'' +
                ", scoreValue=" + scoreValue +
                ", examDate=" + examDate +
                '}';
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Double getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Double scoreValue) {
        this.scoreValue = scoreValue;
    }

    public Scores(Long scoreId, LocalDate examDate, Double scoreValue, String courseName, Student student) {
        this.scoreId = scoreId;
        this.examDate = examDate;
        this.scoreValue = scoreValue;
        this.courseName = courseName;
        this.student = student;
    }

    public Scores() {
    }
}