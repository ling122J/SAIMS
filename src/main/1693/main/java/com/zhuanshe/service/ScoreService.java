package com.zhuanshe.service;

import com.zhuanshe.dao.ScoresDao;
import com.zhuanshe.dao.StudentDao;
import com.zhuanshe.entity.Scores;
import com.zhuanshe.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    @Autowired
    private ScoresDao scoresDao;

    @Autowired
    private StudentDao studentDao;

    // 查询学生所有成绩
    public List<Scores> getStudentScores(Integer studentId) {
        return scoresDao.findByStudentId(studentId);
    }

    // 查询科目不合格学生信息
    public List<Student> getFailStudents(String courseName) {
        List<Scores> failScores = scoresDao.findFailScoresByCourse(courseName);
        return failScores.stream()
                .map(Scores::getStudent)
                .distinct()
                .collect(Collectors.toList());
    }
}