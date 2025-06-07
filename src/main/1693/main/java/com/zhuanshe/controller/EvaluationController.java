package com.zhuanshe.controller;

import com.zhuanshe.entity.*;
import com.zhuanshe.service.EvaluationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    // 教师提交评价
    @PostMapping("/submit")
    public ResponseEntity<?> submitEvaluation(@RequestParam String content, 
                                            @RequestParam Long studentId,
                                            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Teacher teacher = currentUser.getTeacher(); // 需要User实体中添加teacher关联
        Student student = new Student();
        student.setUserId(studentId);
        
        Evaluations eval = evaluationService.submitEvaluation(teacher, content, student);
        return ResponseEntity.ok(eval);
    }

    // 获取待审核列表（管理员用）
    @GetMapping("/pending")
    public List<Evaluations> getPendingEvaluations() {
        return evaluationService.getPendingEvaluations();
    }

    // 审核评价
    @PostMapping("/audit/{evalId}")
    public Evaluations auditEvaluation(@PathVariable Long evalId,
                                      @RequestParam Evaluations.AuditStatus status,
                                      HttpSession session) {
        Admin admin = (Admin) session.getAttribute("currentAdmin");
        return evaluationService.auditEvaluation(evalId, status, admin);
    }

    // 学生查看评价
    @GetMapping("/my")
    public List<Evaluations> getMyEvaluations(HttpSession session) {
        Student student = (Student) session.getAttribute("currentStudent");
        return evaluationService.getVisibleEvaluations(student);
    }

    // 教师修改/删除评价
    @PostMapping("/update/{evalId}")
    public ResponseEntity<?> updateEvaluation(@PathVariable Long evalId,
                                            @RequestParam String content,
                                            HttpSession session) {
        User currentUser = (User) session.getAttribute("currentUser");
        Evaluations eval = evaluationService.getEvaluationById(evalId);
        
        // 修改为通过用户ID比对而不是直接获取教师对象
        if(eval.getTeacher().getUser().getUserid().equals(currentUser.getUserid())) {
            if(content.isBlank()) {
                evaluationService.deleteEvaluation(evalId);
                return ResponseEntity.ok("评价已删除");
            }
            eval.setContent(content);
            eval.setStatus(Evaluations.AuditStatus.PENDING); // 修改后需重新审核
            return ResponseEntity.ok(evaluationService.saveEvaluation(eval));
        }
        return ResponseEntity.status(403).body("无权限操作");
    }
}