package com.seckilldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tortoise
 * @Date 2025/7/11 13:00
 * @PackageName:com.seckilldemo.controller
 * @ClassName: TestController
 * @Description: 测试控制器
 * @Version 1.0
 */
@Controller
@RequestMapping("/test")
public class TestController {

    /**
     * 跳转到加密测试页面
     */
    @RequestMapping("/encrypt")
    public String encryptTest() {
        return "encrypt_test";
    }
} 