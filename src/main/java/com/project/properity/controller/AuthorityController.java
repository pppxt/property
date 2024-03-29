package com.project.properity.controller;

import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Result;
import com.project.properity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 权限Controller
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String username){
        PageBean pageBean = userService.page(pageNum,pageSize,username);
        return Result.success(pageBean);
    }

}
