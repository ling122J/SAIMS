package com.example.grade.mapper;

import com.example.grade.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO student(student_id, name, class_name, major, email, phone) " +
            "VALUES(#{studentId}, #{name}, #{className}, #{major}, #{email}, #{phone})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Select("SELECT * FROM student WHERE id = #{id}")
    Student selectById(Long id);

    @Select("SELECT * FROM student WHERE student_id = #{studentId}")
    Student selectByStudentId(String studentId);

    @Select("SELECT * FROM student")
    List<Student> selectAll();

    @Update("UPDATE student SET name = #{name}, class_name = #{className}, " +
            "major = #{major}, email = #{email}, phone = #{phone} " +
            "WHERE id = #{id}")
    int update(Student student);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int deleteById(Long id);
} 