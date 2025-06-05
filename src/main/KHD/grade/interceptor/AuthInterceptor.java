package com.example.grade.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的URI
        String uri = request.getRequestURI();
        
        // 不需要登录就可以访问的路径
        if (uri.contains("/login") || uri.contains("/register") || uri.contains("/static/")) {
            return true;
        }
        
        // 检查用户是否已登录
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            return true;
        }
        
        // 未登录则重定向到登录页面
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后，视图渲染之前调用
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完成之后调用，也就是在视图渲染完成之后
    }
} 