package com.example.grade.mapper;

import com.example.grade.entity.OperationLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    @Insert("INSERT INTO operation_log(user_id, operation_type, operation_content, ip_address) " +
            "VALUES(#{userId}, #{operationType}, #{operationContent}, #{ipAddress})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(OperationLog operationLog);

    @Select("SELECT * FROM operation_log WHERE id = #{id}")
    OperationLog selectById(Long id);

    @Select("SELECT * FROM operation_log WHERE user_id = #{userId}")
    List<OperationLog> selectByUserId(Long userId);

    @Select("SELECT * FROM operation_log ORDER BY create_time DESC")
    List<OperationLog> selectAll();

    @Select("SELECT * FROM operation_log WHERE operation_type = #{operationType}")
    List<OperationLog> selectByOperationType(String operationType);
} 