package com.student.management.service;

import com.student.management.entity.Score;
import com.student.management.mapper.ScoreMapper;
import com.student.management.service.impl.ScoreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreServiceTest {
    
    @Mock
    private ScoreMapper scoreMapper;
    
    @InjectMocks
    private ScoreServiceImpl scoreService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    // 测试提交成绩功能
    void testSubmitScore() {
        Score score = new Score();
        score.setStudentId("2023001");
        score.setChinese(85);
        score.setMath(75);
        score.setEnglish(55);
        
        when(scoreMapper.insert(score)).thenReturn(1);
        
        boolean result = scoreService.submitScore(score);
        assertTrue(result);
        verify(scoreMapper, times(1)).insert(score);
    }
    
    @Test
    //测试检查低分警告功能
    void testCheckLowScoreAlert() {
        Score score = new Score();
        score.setStudentId("2023001");
        score.setChinese(85);
        score.setMath(75);
        score.setEnglish(55);
        
        when(scoreMapper.selectById("2023001")).thenReturn(score);
        
        boolean hasLowScore = scoreService.checkLowScoreAlert("2023001");
        assertTrue(hasLowScore);
    }
    
    @Test
    //测试批准成绩功能
    void testApproveScore() {
        String studentId = "2023001";
        
        when(scoreMapper.updateStatus(studentId, "APPROVED")).thenReturn(1);
        
        boolean result = scoreService.approveScore(studentId);
        assertTrue(result);
        verify(scoreMapper, times(1)).updateStatus(studentId, "APPROVED");
    }
    
    @Test
    //测试拒绝成绩功能
    void testRejectScore() {
        String studentId = "2023001";
        
        when(scoreMapper.updateStatus(studentId, "REJECTED")).thenReturn(1);
        
        boolean result = scoreService.rejectScore(studentId);
        assertTrue(result);
        verify(scoreMapper, times(1)).updateStatus(studentId, "REJECTED");
    }
} 