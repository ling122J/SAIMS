package com.example.controller;

import com.example.entity.authority.Permission;
import com.example.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Resource
    PermissionService permissionService;

    // 分页查询权限列表（POST + 分页参数在请求体）
    @PostMapping("/list")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String,Object>> showPermissions(@RequestBody Map<String, Integer> params) {
        int page = params.getOrDefault("page", 1);
        int size = params.getOrDefault("size", 10);
        return permissionService.getPermissions(page, size);
    }

    // 删除权限（通过请求体接收pid）
    @PostMapping("/delete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deletePermission(@RequestBody Map<String, Integer> request) {
        int pid = request.get("pid");
        return permissionService.deletePermission(pid);
    }

    // 添加权限接口
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addPermission(@RequestBody Permission permission) {
        return permissionService.addPermission(permission);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String,Object>> updatePermission(@RequestBody Permission permission) {
        return permissionService.updatePermission(permission);
    }
}
