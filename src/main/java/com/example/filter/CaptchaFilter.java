package com.example.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String URI = request.getRequestURI();
        String captcha = request.getParameter("captcha");
        String code = (String)request.getSession().getAttribute("captcha");
        if(URI.equals("/user/login")) {   //登录请求接受验证码
            // 验证码不匹配（忽略大小写）
            if (!code.equalsIgnoreCase(captcha)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "验证码错误");
                return;
            }
        }
        filterChain.doFilter(request,response);
    }
}
