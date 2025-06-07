package com.zhuanshe.dao;

import com.zhuanshe.entity.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoresDao extends JpaRepository<Scores, Long> {
    
    // 查询指定学生的所有成绩
    @Query("SELECT s FROM Scores s WHERE s.student.user.userid = ?1")
    List<Scores> findByStudentId(Integer studentId);

    // 查询指定科目不合格成绩（<60）
    @Query("SELECT s FROM Scores s WHERE s.courseName = ?1 AND s.scoreValue < 60.0")
    List<Scores> findFailScoresByCourse(String courseName);
}