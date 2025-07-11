package com.seckilldemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seckilldemo.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:46
 * @PackageName:com.seckilldemo.service
 * @ClassName: UserService
 * @Description:
 * @Version 1.0
 */

public interface UserService extends IService<User> {


    String doLogin(String mobile, String password, String encryptedPassword, HttpSession session, Model model);
}
