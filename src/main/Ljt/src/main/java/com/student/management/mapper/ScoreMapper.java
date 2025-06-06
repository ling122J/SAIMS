package com.student.management.mapper;

import com.student.management.entity.Score;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreMapper {
    int insert(Score score);
    Score selectById(String studentId);
    int updateStatus(String studentId, String status);
} 