package com.zhuanshe.service;

import com.zhuanshe.entity.User;
import com.zhuanshe.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long LOCK_TIME_MINUTES = 15;
    private final Map<String, Integer> attemptCache = new ConcurrentHashMap<>();
    private final Map<String, Long> lockCache = new ConcurrentHashMap<>();

    @Autowired
    private UserDao userDao;

    // 添加登录验证方法
    public User login(String username, String password, String ip) {
        // 检查IP是否被锁定
        if (isIpLocked(ip)) {
            throw new RuntimeException("账户已锁定，请" + getRemainingLockTime(ip) + "分钟后重试");
        }

        User user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            attemptCache.remove(ip);
            return user;
        }

        // 记录失败次数
        int attempts = attemptCache.getOrDefault(ip, 0) + 1;
        attemptCache.put(ip, attempts);

        if (attempts >= MAX_ATTEMPTS) {
            lockCache.put(ip, System.currentTimeMillis());
            throw new RuntimeException("连续登录失败超过" + MAX_ATTEMPTS + "次，账户已锁定15分钟");
        }

        throw new RuntimeException("用户名或密码错误，剩余尝试次数：" + (MAX_ATTEMPTS - attempts));
    }
    //判断给定的IP是否被锁定
    private boolean isIpLocked(String ip) {
        Long lockTime = lockCache.get(ip);
        if (lockTime != null) {
            long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - lockTime);
            if (elapsedMinutes < LOCK_TIME_MINUTES) {
                return true;
            }
            lockCache.remove(ip);
        }
        return false;
    }
//获取剩余锁定时间
    private long getRemainingLockTime(String ip) {
        Long lockTime = lockCache.get(ip);
        if (lockTime != null) {
            long elapsed = System.currentTimeMillis() - lockTime;
            return LOCK_TIME_MINUTES - TimeUnit.MILLISECONDS.toMinutes(elapsed);
        }
        return 0;
    }

    // 新增注册方法
    public String register(String username, String password, String email) {
        if (userDao.findByUsername(username) != null) {
            return "用户名已存在";
        }
        
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setRole("user");
        newUser.setCreatedAt(java.time.LocalDateTime.now().toString());
        newUser.setUpdatedAt(java.time.LocalDateTime.now().toString());
        
        userDao.save(newUser);
        return "注册成功";
    }
}