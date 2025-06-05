package com.example.grade.mapper;

import com.example.grade.entity.Grade;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GradeMapper {
    @Insert("INSERT INTO grade(student_id, course_name, score, credit, semester) " +
            "VALUES(#{studentId}, #{courseName}, #{score}, #{credit}, #{semester})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Grade grade);

    @Select("SELECT * FROM grade WHERE id = #{id}")
    Grade selectById(Long id);

    @Select("SELECT * FROM grade WHERE student_id = #{studentId}")
    List<Grade> selectByStudentId(String studentId);

    @Select("SELECT * FROM grade WHERE student_id = #{studentId} AND semester = #{semester}")
    List<Grade> selectByStudentIdAndSemester(@Param("studentId") String studentId, @Param("semester") String semester);

    @Update("UPDATE grade SET course_name = #{courseName}, score = #{score}, " +
            "credit = #{credit}, semester = #{semester} WHERE id = #{id}")
    int update(Grade grade);

    @Delete("DELETE FROM grade WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT AVG(score) FROM grade WHERE student_id = #{studentId}")
    Double calculateAverageScore(String studentId);

    @Select("SELECT SUM(score * credit) / SUM(credit) FROM grade WHERE student_id = #{studentId}")
    Double calculateGPA(String studentId);

    @Select("SELECT * FROM grade WHERE student_id = #{studentId}")
    List<Grade> findByStudentId(String studentId);

    @Select("SELECT g.* FROM grade g " +
            "JOIN student s ON g.student_id = s.student_id " +
            "WHERE s.class_name = #{className}")
    List<Grade> findByClassName(String className);

    @Select("SELECT * FROM grade WHERE course_name = #{courseName}")
    List<Grade> findByCourseName(String courseName);

    @Select("SELECT * FROM grade WHERE semester = #{semester}")
    List<Grade> findBySemester(String semester);

    @Delete("DELETE FROM grade WHERE id = #{id}")
    void delete(Long id);
} 