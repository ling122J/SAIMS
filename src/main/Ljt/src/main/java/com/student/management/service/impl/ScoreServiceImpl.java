package com.student.management.service.impl;

import com.student.management.entity.Score;
import com.student.management.mapper.ScoreMapper;
import com.student.management.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService {
    
    @Autowired
    private ScoreMapper scoreMapper;
    
    @Override
    // 调用mapper的insert方法，返回影响的行数
    // 如果大于0表示提交成功
    public boolean submitScore(Score score) {
        return scoreMapper.insert(score) > 0;
    }
    
    @Override
    public boolean checkLowScoreAlert(String studentId) {
        // 检查学生成绩
        Score score = scoreMapper.selectById(studentId);
        if (score != null) {
            return score.getChinese() < 60 || 
                   score.getMath() < 60 || 
                   score.getEnglish() < 60;
        }
        return false;
    }
    
    @Override
    public boolean approveScore(String studentId) {
        // 更新成绩状态为已审核通过
        return scoreMapper.updateStatus(studentId, "APPROVED") > 0;
    }
    
    @Override
    public boolean rejectScore(String studentId) {
        // 更新成绩状态为已审核拒绝
        return scoreMapper.updateStatus(studentId, "REJECTED") > 0;
    }
} 