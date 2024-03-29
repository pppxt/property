package com.project.properity.controller;

import com.github.pagehelper.PageInfo;
import com.project.properity.common.AutoLog;
import com.project.properity.pojo.*;
import com.project.properity.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 日志
 */
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/page")
    public Result page(User params){
        PageInfo<Log> pageInfo = logService.page(params);
        return Result.success(pageInfo);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        logService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Log log){
        logService.save(log);
        return Result.success();
    }
}
