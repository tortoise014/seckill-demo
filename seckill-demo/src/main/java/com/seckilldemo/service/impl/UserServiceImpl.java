package com.seckilldemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seckilldemo.controller.LoginController;
import com.seckilldemo.mapper.UserMapper;
import com.seckilldemo.pojo.User;
import com.seckilldemo.service.UserService;
import com.seckilldemo.utils.MD5Util;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * @Author tortoise
 * @Date 2025/7/11 11:47
 * @PackageName:com.seckilldemo.service.impl
 * @ClassName: UserServiceImpl
 * @Description:
 * @Version 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Override
    public String doLogin(String mobile, String password, String encryptedPassword, HttpSession session, Model model) {
        // 参数验证
        log.info("手机号：{}", mobile);
        log.info("密码：{}", encryptedPassword);
        if (!StringUtils.hasText(mobile)) {
            model.addAttribute("error", "手机号不能为空");
            return "login";
        }

        // 确定使用哪个密码字段（优先使用加密后的密码）
        String inputPassword = StringUtils.hasText(encryptedPassword) ? encryptedPassword : password;
        boolean isEncrypted = StringUtils.hasText(encryptedPassword);

        if (!StringUtils.hasText(inputPassword)) {
            model.addAttribute("error", "密码不能为空");
            return "login";
        }

        // 手机号格式验证
        if (!mobile.matches("^1[3-9]\\d{9}$")) {
            model.addAttribute("error", "手机号格式不正确");
            return "login";
        }

        // 如果是明文密码，验证长度
        if (!isEncrypted && inputPassword.length() < 6) {
            model.addAttribute("error", "密码长度不能少于6位");
            return "login";
        }

        try {
            // 根据手机号查询用户
            User user = getById(Long.parseLong(mobile));

            if (user == null) {
                model.addAttribute("error", "用户不存在");
                return "login";
            }

            // 验证密码
            String finalPassword;
            if (isEncrypted) {
                // 前端已加密，只需要进行第二次加密（formPassToDBPass）
                finalPassword = MD5Util.formPassToDBPass(inputPassword, user.getSalt());
            } else {
                // 明文密码，需要完整的双重加密
                finalPassword = MD5Util.inputPassToDBPass(inputPassword, user.getSalt());
            }

            if (!finalPassword.equals(user.getPassword())) {
                model.addAttribute("error", "密码错误");
                return "login";
            }

            // 登录成功，将用户信息存入session
            session.setAttribute("user", user);

            // 更新登录信息
            user.setLastLoginDate(new java.util.Date());
            user.setLoginCount(user.getLoginCount() == null ? 1 : user.getLoginCount() + 1);
            updateById(user);

            // 登录成功后跳转到首页或其他页面
            return "redirect:/goods/toList";

        } catch (NumberFormatException e) {
            model.addAttribute("error", "手机号格式不正确");
            return "login";
        } catch (Exception e) {
            log.error("登录异常:", e);
            model.addAttribute("error", "登录失败，请稍后重试");
            return "login";
        }
    }
}

