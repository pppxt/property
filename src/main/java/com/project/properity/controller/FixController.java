package com.project.properity.controller;

import cn.hutool.core.lang.Dict;
import com.github.pagehelper.PageInfo;
import com.project.properity.pojo.*;
import com.project.properity.service.FixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报修
 */
@RestController
@RequestMapping("fix")
public class FixController {

    @Autowired
    private FixService fixService;

    @GetMapping("/page")
    public Result page(User params){
        PageInfo<Fix> pageInfo = fixService.page(params);
        return Result.success(pageInfo);
    }

    //查询reason
    @GetMapping("/list")
    public Result list(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String reason){
        PageBean pageBean = fixService.list(pageNum,pageSize,reason);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        fixService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Fix fix){
        fixService.save(fix);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Fix fix){
        fixService.update(fix);
        return Result.success();
    }

    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Fix> list = fixService.findAll(); // 已经查询出表中所有数据

        //次数
        Map<String, Integer> valueCountMap = new HashMap<>();
        // 遍历List并统计字段值出现的次数
        for (Fix fix : list) {
            String fieldValue = fix.getTime();
            if (valueCountMap.containsKey(fieldValue)) {
                int count = valueCountMap.get(fieldValue);
                valueCountMap.put(fieldValue, count + 1);
            } else {
                valueCountMap.put(fieldValue, 1);
            }
        }
        // 将数据包装为适用于前端的格式，以对象数组的形式存储数据
        List<Dict> lineData = new ArrayList<>();
        List<Dict> barData = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            Dict dataItem = Dict.create().set("time", entry.getKey()).set("value", entry.getValue());
            lineData.add(dataItem); //折线
            barData.add(dataItem); //柱状和饼图
        }

        Dict res = Dict.create().set("line", lineData).set("bar", barData);
        return Result.success(res);
    }
}
