package com.zhuanshe.dao;

import com.zhuanshe.entity.Evaluations;
import com.zhuanshe.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvaluationDao extends JpaRepository<Evaluations, Long> {
    List<Evaluations> findByStatus(Evaluations.AuditStatus status);
    List<Evaluations> findByStudentAndStatus(Student student, Evaluations.AuditStatus status);
}