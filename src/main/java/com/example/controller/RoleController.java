package com.example.controller;


import com.example.entity.authority.BindRolesRequest;
import com.example.entity.authority.BindUsersRequest;
import com.example.entity.authority.Role;
import com.example.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("/bindRoles")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, Object>> bindRoles(@RequestBody BindRolesRequest request) {
        return roleService.bindRoles(request.getUid(),request.getRidList());
    }

    @PostMapping("/bindUsers")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Map<String, Object>> bindUsers(@RequestBody BindUsersRequest request) {
        return roleService.bindUsers(request.getRid(), request.getUidList());
    }

    //拿到角色列表
    @PostMapping("/list")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> getAllRoles(@RequestBody Map<String, Integer> params) {
        int page = params.getOrDefault("page", 1);
        int size = params.getOrDefault("size", 10);
        return roleService.findAllRoles(page, size);
    }

    // 删除角色（通过请求体接收rid）
    @PostMapping("/delete")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> deleteRole(@RequestBody Map<String, Integer> request) {
        int rid = request.get("rid");
        return roleService.deleteRole(rid);
    }

    // 更新角色（通过请求体接收完整对象）
    @PostMapping("/update")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<?> updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }
}