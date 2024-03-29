package com.project.properity.controller;

import com.project.properity.pojo.Building;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Result;
import com.project.properity.service.BuildingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 楼栋管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    //分页查询
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String name,
                       String comName){
        PageBean pageBean = buildingService.page(pageNum,pageSize,name,comName);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        buildingService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Building building){
        buildingService.save(building);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Building building){
        buildingService.update(building);
        return Result.success();
    }
}
