package com.example.service;

import com.example.entity.authority.Account;
import com.example.entity.authority.Role;
import com.example.mapper.AccountMapper;
import com.example.mapper.RoleMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
@Transactional
public class AccountService implements UserDetailsService {
    @Resource
    private AccountMapper accountMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountMapper.findByUsername(username);
        if (account == null) throw new UsernameNotFoundException("无用户信息");
        //查询该用户的角色信息
        List<Role> roleList = roleMapper.findRoles(account.getUid());
        account.setRoleList(roleList);
        return account;
    }

    public boolean userExists(String username) {
        return accountMapper.findByUsername(username) != null;
    }

    public Map<String, Object> checkUsername(String username) {
        Map<String, Object> result = new HashMap<>();
        boolean exists = userExists(username);
        result.put("available", !exists);
        return result;
    }

    public ResponseEntity<Map<String, Object>> createUser(Map<String, String> params, HttpServletRequest request) throws Exception {
        Map<String, Object> result = new HashMap<>();
        String username = params.get("username");
        String password = params.get("password");
        // 1. 验证码校验（关键安全校验）
        String sessionCode = (String) request.getSession().getAttribute("captcha");
        String inputCode = params.get("captcha");
        if (!StringUtils.hasText(inputCode) || !inputCode.equalsIgnoreCase(sessionCode)) {
            result.put("code", 400);
            result.put("msg", "验证码错误");
            return ResponseEntity.badRequest().body(result);
        }
        // 最终唯一性检查（防止绕过前端校验）
        if (userExists(username)) {
            result.put("code", 409);
            result.put("msg", "用户名已被注册");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        }
        // 创建用户
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setEnabled(true);
        account.setCreateTime(new Date());
        int count = accountMapper.insertOne(account);
        result.put("code", 200);
        result.put("msg", "注册成功");
        result.put("insertSize", count);
        return ResponseEntity.ok(result);
    }
    public ResponseEntity<Map<String, Object>> getUsers(Integer page, Integer size) {
        Integer pageStart = (page - 1) * size;
        List<Account> accounts = accountMapper.findByConditions(new Account(), pageStart, size);
        int total = accountMapper.selectTotalCount(); // 总记录数

        // 补充角色信息
        for (Account account : accounts) {
            List<Role> roles = roleMapper.findRoles(account.getUid());
            account.setRoleList(roles != null ? roles : new ArrayList<>());
        }
        return ResponseEntity.ok(Map.of(
                "code", 0,
                "message", "用户列表",
                "count", total, // 返回总记录数
                "data", accounts
        ));
    }

    public void changePassword(String oldPassword, String newPassword) {
        // 获取当前登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Account account = accountMapper.findByUsername(username);

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, account.getPassword())) {
            throw new IllegalArgumentException("旧密码错误");
        }

        // 更新为新密码
        account.setPassword(passwordEncoder.encode(newPassword));
        accountMapper.updateUser(account);
    }


}
