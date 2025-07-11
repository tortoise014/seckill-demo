package com.seckilldemo.controller;

import com.seckilldemo.dto.Result;
import com.seckilldemo.pojo.User;
import com.seckilldemo.service.UserService;
import com.seckilldemo.utils.MD5Util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpSession;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:48
 * @PackageName:com.seckilldemo.controller
 * @ClassName: LoginController
 * @Description: 登录控制器
 * @Version 1.0
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * 跳转到登录页面
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 处理登录请求
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@RequestParam("mobile") String mobile,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "encryptedPassword", required = false) String encryptedPassword,
                          HttpSession session,
                          Model model) {
        return userService.doLogin(mobile, password, encryptedPassword, session, model);
    }
    
    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        session.invalidate();
        return "redirect:/login/toLogin";
    }
}
