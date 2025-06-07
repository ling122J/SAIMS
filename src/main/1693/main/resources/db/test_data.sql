-- 学生表测试数据（假设user_id已存在关联用户）
INSERT INTO students (user_id, student_id, class_name) VALUES
(1, 'S2023001', '三年一班'),
(2, 'S2023002', '三年二班'),
(3, 'S2023003', '三年三班');

-- 成绩表测试数据（假设student_id关联students表的user_id）
INSERT INTO scores (student_id, course_name, score_value, exam_date) VALUES
(1, '语文', 85.5, '2023-06-15'),
(1, '数学', 92.0, '2023-06-16'),
(2, '英语', 58.5, '2023-06-15'),  -- 不合格成绩
(2, '数学', 45.0, '2023-06-16'),  -- 不合格成绩
(3, '语文', 76.0, '2023-06-15'),
(3, '英语', 89.5, '2023-06-16');