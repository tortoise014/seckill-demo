package com.seckilldemo.controller;

import com.seckilldemo.dto.Result;
import com.seckilldemo.dto.ResultCode;
import com.seckilldemo.dto.PageResult;
import com.seckilldemo.exception.BusinessException;
import com.seckilldemo.pojo.User;
import com.seckilldemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author tortoise
 * @Date 2025/7/11 14:00
 * @PackageName:com.seckilldemo.controller
 * @ClassName: ApiController
 * @Description: API控制器示例 - 展示统一结果集的使用
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息 - 成功返回数据
     */
    @GetMapping("/user/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_FOUND);
        }
        return Result.success(user);
    }

    /**
     * 获取用户列表 - 返回分页数据
     */
    @GetMapping("/users")
    public Result<PageResult<User>> getUserList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer pageSize) {
        // 模拟分页查询
        List<User> users = userService.list();
        PageResult<User> pageResult = PageResult.of(pageNum, pageSize, (long) users.size(), users);
        return Result.success(pageResult);
    }

    /**
     * 创建用户 - 成功返回
     */
    @PostMapping("/user")
    public Result<String> createUser(@RequestBody User user) {
        boolean success = userService.save(user);
        if (success) {
            return Result.success("用户创建成功");
        } else {
            return Result.failed("用户创建失败");
        }
    }

    /**
     * 更新用户 - 带自定义消息
     */
    @PutMapping("/user/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User existingUser = userService.getById(id);
        if (existingUser == null) {
            return Result.failed(ResultCode.USER_NOT_FOUND);
        }
        
        user.setId(id);
        boolean success = userService.updateById(user);
        if (success) {
            return Result.success(user, "用户信息更新成功");
        } else {
            return Result.failed("用户信息更新失败");
        }
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/user/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        User user = userService.getById(id);
        if (user == null) {
            return Result.failed(ResultCode.USER_NOT_FOUND);
        }
        
        boolean success = userService.removeById(id);
        if (success) {
            return Result.success();
        } else {
            return Result.failed("用户删除失败");
        }
    }

    /**
     * 参数验证失败示例
     */
    @PostMapping("/validate")
    public Result<String> validateExample(@RequestParam String mobile) {
        if (mobile == null || mobile.trim().isEmpty()) {
            return Result.validateFailed("手机号不能为空");
        }
        
        if (!mobile.matches("^1[3-9]\\d{9}$")) {
            return Result.validateFailed("手机号格式不正确");
        }
        
        return Result.success("验证通过");
    }

    /**
     * 业务异常示例
     */
    @GetMapping("/exception")
    public Result<String> exceptionExample() {
        // 抛出业务异常，会被全局异常处理器捕获
        throw new BusinessException(ResultCode.SYSTEM_BUSY, "这是一个业务异常示例");
    }

    /**
     * 系统异常示例
     */
    @GetMapping("/error")
    public Result<String> errorExample() {
        // 抛出运行时异常，会被全局异常处理器捕获
        throw new RuntimeException("这是一个系统异常示例");
    }

    /**
     * 返回复杂数据结构
     */
    @GetMapping("/complex")
    public Result<Map<String, Object>> complexDataExample() {
        Map<String, Object> data = Map.of(
            "message", "这是复杂数据示例",
            "count", 100,
            "active", true,
            "items", List.of("item1", "item2", "item3")
        );
        return Result.success(data);
    }
} 