package com.student.management.service;

import com.student.management.entity.Score;

public interface ScoreService {
    boolean submitScore(Score score);
    boolean checkLowScoreAlert(String studentId);
    boolean approveScore(String studentId);
    boolean rejectScore(String studentId);
} 