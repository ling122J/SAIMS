package com.zhuanshe.service;

import com.zhuanshe.dao.EvaluationDao;
import com.zhuanshe.entity.Admin;
import com.zhuanshe.entity.Evaluations;
import com.zhuanshe.entity.Student;
import com.zhuanshe.entity.Teacher;
import com.zhuanshe.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationDao evaluationDao;

    // 根据ID获取评价方法
    public Evaluations getEvaluationById(Long evalId) {
        return evaluationDao.findById(evalId).orElseThrow(() ->
            new RuntimeException("未找到对应评价"));
    }

    // 删除评价方法
    public void deleteEvaluation(Long evalId) {
        evaluationDao.deleteById(evalId);
    }

    // 保存评价方法（补充显式声明）
    public Evaluations saveEvaluation(Evaluations evaluation) {
        return evaluationDao.save(evaluation);
    }

    // 教师提交评价
    public Evaluations submitEvaluation(Teacher teacher, String content, Student student) {
        Evaluations eval = new Evaluations();
        eval.setTeacher(teacher);
        eval.setStudent(student);
        eval.setContent(content);
        eval.setStatus(Evaluations.AuditStatus.PENDING);
        eval.setAuditTime(null);
        return evaluationDao.save(eval);
    }

    // 获取待审核评价
    public List<Evaluations> getPendingEvaluations() {
        return evaluationDao.findByStatus(Evaluations.AuditStatus.PENDING);
    }

    // 审核评价
    public Evaluations auditEvaluation(Long evalId, Evaluations.AuditStatus status, Admin auditor) {
        Evaluations eval = evaluationDao.findById(evalId).orElseThrow();
        eval.setStatus(status);
        eval.setAuditor(auditor);
        eval.setAuditTime(LocalDateTime.now());
        return evaluationDao.save(eval);
    }

    // 获取学生可见评价
    public List<Evaluations> getVisibleEvaluations(Student student) {
        return evaluationDao.findByStudentAndStatus(student, Evaluations.AuditStatus.APPROVED);
    }
}