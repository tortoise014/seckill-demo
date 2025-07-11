package com.seckilldemo.controller;

import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:00
 * @PackageName:com.seckilldemo.controller
 * @ClassName: healthController
 * @Description:
 * @Version 1.0
 */
@Controller
@RequestMapping("/health")
public class healthController {
    @RequestMapping("/check")
    public String check(){
        return "health";
    }
}
