package com.student.management.controller;

import com.student.management.entity.Score;
import com.student.management.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private ScoreService scoreService;

    // 提交成绩
    @PostMapping("/submit")
    public ResponseEntity<String> submitScore(@RequestBody Score score) {
        boolean result = scoreService.submitScore(score);
        return result ? ResponseEntity.ok("提交成功") : ResponseEntity.badRequest().body("提交失败");
    }

    // 成绩预警
    @GetMapping("/alert")
    public ResponseEntity<String> checkLowScoreAlert(@RequestParam String studentId) {
        // 调用service的方法
        boolean alert = scoreService.checkLowScoreAlert(studentId);
        return alert ? ResponseEntity.ok("有科目低于60分") : ResponseEntity.ok("全部及格");
    }

    // 审核通过
    @PostMapping("/approve")
    public ResponseEntity<String> approveScore(@RequestParam String studentId) {
        boolean result = scoreService.approveScore(studentId);
        return result ? ResponseEntity.ok("审核通过") : ResponseEntity.badRequest().body("审核失败");
    }

    // 审核拒绝
    @PostMapping("/reject")
    public ResponseEntity<String> rejectScore(@RequestParam String studentId) {
        boolean result = scoreService.rejectScore(studentId);
        return result ? ResponseEntity.ok("审核拒绝") : ResponseEntity.badRequest().body("操作失败");
    }
} 