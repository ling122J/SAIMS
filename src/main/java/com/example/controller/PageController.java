package com.example.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import cn.hutool.captcha.generator.CodeGenerator;
import com.example.service.AccountService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
public class PageController {

    @RequestMapping("/Generate")
    public void generatePicture(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("image/jpeg");
        ICaptcha captcha = CaptchaUtil.createGifCaptcha(90, 30, new CodeGenerator() {
            @Override
            public String generate() {
                int num = 1000 + new Random().nextInt(9000);
                return String.valueOf(num);
            }
            @Override
            public boolean verify(String s, String s1) {
                return false;
            }
        }, 4);
        req.getSession().setAttribute("captcha", captcha.getCode());
        captcha.write(res.getOutputStream());
    }

    @RequestMapping("/home")       //主页
    public String spacePage() {
        return "/main";
    }


    @RequestMapping("/toLogin")     //登录页
    public String loginPage() {
        return "/index";
    }


}
