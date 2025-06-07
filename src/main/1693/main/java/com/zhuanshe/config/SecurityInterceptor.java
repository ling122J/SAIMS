package com.zhuanshe.config;

import com.zhuanshe.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component // 添加该注解使拦截器成为Spring管理的Bean
public class SecurityInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("currentUser");
        
        String uri = request.getRequestURI();
        // 添加评价接口权限控制
        if(uri.startsWith("/evaluation")) {
            if(user == null) {
                response.sendRedirect("/index.html");
                return false;
            }
            // 管理员才能访问审核接口
            if(uri.contains("/audit") && !"admin".equals(user.getRole())) {
                response.sendError(403);
                return false;
            }
        }
        // 排除登录和注册接口
        if (uri.contains("/login") || uri.contains("/register") || uri.endsWith(".html") || uri.endsWith(".css")) {
            return true;
        }
        
        if (user == null) {
            response.sendRedirect("/index.html");
            return false;
        }
        return true;
    }
}