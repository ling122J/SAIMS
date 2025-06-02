package com.example.config;

import com.example.filter.CaptchaFilter;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Resource
    private CaptchaFilter captchaFilter;

    @Bean       //配置spring security密码加密的实现类
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain0(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/static/**","/Generate","/Through","/checkUsername").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(conf -> {
                    conf.loginPage("/toLogin");   //使用url:/user/login返回的自定义界面index
                    conf.loginProcessingUrl("/user/login"); //登录数据提交地址,security内部自动处理
                    conf.successForwardUrl("/home");   //登录成功转发地址
                    conf.failureHandler((request, response, exception) -> { //验证失败时处理
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                    });
                    conf.permitAll();
                })
                .logout(conf -> {
                    conf.logoutUrl("/toLogout");  //这里接口任意,配置了logoutSuccessUrl
                    conf.logoutSuccessUrl("/toLogin");
                    conf.permitAll();
                })
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
