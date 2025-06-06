package com.student.management.entity;

public class Score {
    private String studentId;
    private Integer chinese;
    private Integer math;
    private Integer english;
    private String status;
    
    // Getters and Setters
    public String getStudentId() {
        return studentId;
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    
    public Integer getChinese() {
        return chinese;
    }
    
    public void setChinese(Integer chinese) {
        this.chinese = chinese;
    }
    
    public Integer getMath() {
        return math;
    }
    
    public void setMath(Integer math) {
        this.math = math;
    }
    
    public Integer getEnglish() {
        return english;
    }
    
    public void setEnglish(Integer english) {
        this.english = english;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
} 