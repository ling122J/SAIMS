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

/**
 * @author ling122J
 * Spring Security的核心配置类
 */
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                /**
                 *  关闭跨站请求伪造攻击
                 */
                .csrf(AbstractHttpConfigurer::disable)
                /**
                 *  Spring Security放行以下几个路径下的资源
                 *  /Generate 负责生成验证码
                 *  /Through 负责校验注册数据
                 *  /checkUsername 异步检查用户名是否存在
                 */
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/static/**","/Generate","/Through","/checkUsername").permitAll();
                    auth.anyRequest().authenticated();
                })
                /**
                 *  loginPage  ： 使框架默认登录页失效,接受接口/toLogin返回的自定义界面index
                 *  loginProcessingUrl : 登录数据提交地址,security内部自动处理
                 *  successForwardUrl : 登录成功 转发 地址
                 *  failureHandler : 验证失败时处理
                 */
                .formLogin(conf -> {
                    conf.loginPage("/toLogin");
                    conf.loginProcessingUrl("/user/login");
                    conf.successForwardUrl("/home");
                    conf.failureHandler((request, response, exception) -> {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("application/json");
                    });
                    conf.permitAll();
                })
                .logout(conf -> {
                    conf.logoutUrl("/toLogout");
                    conf.logoutSuccessUrl("/toLogin");
                    conf.permitAll();
                })
                /**
                 * 在提交 注册/登录 表单前 生成验证码
                 */
                .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
