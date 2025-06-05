package com.example.grade.mapper;

import com.example.grade.entity.GpaWarning;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GpaWarningMapper {
    @Insert("INSERT INTO gpa_warning(warning_threshold, is_active) " +
            "VALUES(#{warningThreshold}, #{isActive})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(GpaWarning gpaWarning);

    @Select("SELECT * FROM gpa_warning WHERE id = #{id}")
    GpaWarning selectById(Long id);

    @Select("SELECT * FROM gpa_warning WHERE is_active = true ORDER BY id DESC LIMIT 1")
    GpaWarning selectActiveWarning();

    @Update("UPDATE gpa_warning SET warning_threshold = #{warningThreshold}, " +
            "is_active = #{isActive} WHERE id = #{id}")
    int update(GpaWarning gpaWarning);

    @Update("UPDATE gpa_warning SET is_active = false WHERE is_active = true")
    int deactivateAll();
} 