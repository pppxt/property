package com.project.properity.controller;

import cn.hutool.core.util.StrUtil;
import com.project.properity.common.AuthAccess;
import com.project.properity.common.AutoLog;
import com.project.properity.pojo.Result;
import com.project.properity.pojo.User;
import com.project.properity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录注册
 */
@Slf4j
@RestController
@RequestMapping
public class LoginController {

    @Resource
    UserService userService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("success");
    }

    @PostMapping("/login")
    @AutoLog("登录该系统")
    public Result login(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())) {
            return Result.error("数据输入不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())) {
            return Result.error("数据输入不合法");
        }
        if(user.getUsername().length() > 10 || user.getPassword().length() > 16) {
            return Result.error("账号或密码位数超出限制");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        User user = userService.selectById(id);
        return Result.success(user);
    }

    //更新个人信息
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        userService.update(user);
        return Result.success();
    }

    /**
     * 重置密码
     */
    @AuthAccess
    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }
}
