package com.example.service;

import com.example.entity.authority.Role;
import com.example.mapper.RoleMapper;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {
    @Resource
    RoleMapper roleMapper;

    public ResponseEntity<Map<String, Object>> findAllRoles(Integer page, Integer size) {
        Integer pageStart = (page - 1) * size;
        List<Role> roleList = roleMapper.findByConditions(new Role(), pageStart, size);
        int total = roleMapper.selectTotalCount(); // 总记录数
        return ResponseEntity.ok(Map.of(
                "code", 0,
                "message", "角色列表",
                "count", total, // 返回总记录数
                "data", roleList
        ));
    }

    public ResponseEntity<Map<String,Object>> addRole(Role role) {
        int count = roleMapper.insertOne(role);
        return ResponseEntity.ok(Map.of("code", 0, "message", "角色添加成功" , "count" , count));
    }

    public ResponseEntity<Map<String,Object>> deleteRole(Integer rid) {
        int count = roleMapper.deleteRole(rid);
        return ResponseEntity.ok(Map.of("code", 0, "message", "角色删除成功" , "count" , count));
    }

    public ResponseEntity<Map<String,Object>> updateRole(Role role) {
        int count = roleMapper.updateRole(role);
        return ResponseEntity.ok(Map.of("code", 0, "message", "角色更新成功" , "count" , count));
    }
    public ResponseEntity<Map<String, Object>> bindRoles(Integer uid, List<Integer> ridList) {
        if (ridList == null || ridList.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "message", "角色列表不能为空"));
        }

        // 查询用户已绑定的角色ID
        List<Role> existingRoles = roleMapper.findRoles(uid);
        List<Integer> existingRids = existingRoles.stream()
                .map(Role::getRid)
                .collect(Collectors.toList());

        // 过滤已存在的角色
        List<Integer> newRids = ridList.stream()
                .filter(rid -> !existingRids.contains(rid))
                .collect(Collectors.toList());

        if (newRids.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "message", "所选角色已绑定"));
        }

        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(uid, newRids);

        int count = roleMapper.bindRoles(map);
        return ResponseEntity.ok(Map.of("code", 0, "message", "角色绑定成功", "count", count));
    }

    public ResponseEntity<Map<String, Object>> bindUsers(Integer rid, List<Integer> uidList) {
        if (uidList == null || uidList.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "message", "用户列表不能为空"));
        }
        // 获取已绑定用户
        List<Integer> existingUserIds = roleMapper.findUserIdsByRoleId(rid);
        List<Integer> newUserIds = uidList.stream()
                .filter(uid -> !existingUserIds.contains(uid))
                .collect(Collectors.toList());

        if (newUserIds.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("code", 400, "message", "所选用户已绑定"));
        }
        Map<Integer, List<Integer>> map = new HashMap<>();
        map.put(rid, newUserIds);
        int count = roleMapper.bindUsers(map);
        return ResponseEntity.ok(Map.of("code", 0, "message", "用户绑定成功", "count", count));
    }
}
