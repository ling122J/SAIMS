package com.example.service;

import com.example.entity.authority.Permission;
import com.example.mapper.PermissionMapper;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    public ResponseEntity<Map<String,Object>> getPermissions(Integer page, Integer size) {
        Integer pageStart = (page - 1) * size;
        List<Permission> permissions = permissionMapper.findByConditions(new Permission(), pageStart, size);
        int total = permissionMapper.selectTotalCount(); // 获取总记录数
        return ResponseEntity.ok(Map.of(
                "code", 0,
                "message", "权限列表",
                "count", total,  // 使用总记录数
                "data", permissions
        ));
    }
    public ResponseEntity<Map<String,Object>> addPermission(Permission permission) {
        int count = permissionMapper.insertOne(permission);
        return ResponseEntity.ok(Map.of("code", 0, "message" ,"权限添加成功" , "count", count));
    }
    public ResponseEntity<Map<String,Object>> updatePermission(Permission permission) {
        int count = permissionMapper.updatePermission(permission);
        return ResponseEntity.ok(Map.of("code", 0, "message" ,"权限修改成功" , "count", count));
    }

    public ResponseEntity<Map<String,Object>> deletePermission(Integer pid) {
        int count = permissionMapper.deletePermission(pid);
        return ResponseEntity.ok(Map.of("code", 0, "message" ,"权限删除成功" , "count", count));
    }
}
