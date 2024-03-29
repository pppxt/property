package com.project.properity.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Renter;
import com.project.properity.pojo.Result;
import com.project.properity.service.RenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.math.BigDecimal;


/**
 * 租户管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/renter")
public class RenterController {

    @Autowired
    private RenterService renterService;

    @GetMapping("/page")
    //如果未指定页码，默认为1
    //如果未指定每页记录数，默认为10
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String name,
                       String number,
                       String phone){
        //调用service分页查询
        PageBean pageBean = renterService.page(pageNum,pageSize,name,number,phone);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        renterService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Renter renter){
        renterService.save(renter);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Renter renter){
        renterService.update(renter);
        return Result.success();
    }
    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Renter> list = renterService.findAll();
        Map<String, Integer> valueCountMap = new HashMap<>();
        for (Renter renter : list) {
            String fieldValue = renter.getAddress();
            //Map接口方法containsKey是否包含键
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
        // 遍历set集合获取每一个键值对对象  valueCountMap.entrySet()获取到所有的键值对对象
        //forEach、entrySet、keySet是Map集合的三种遍历方式
        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            Dict dataItem = Dict.create().set("name", entry.getKey()).set("value", entry.getValue());
            lineData.add(dataItem); //折线
            barData.add(dataItem); //柱状和饼图
        }

        Dict res = Dict.create().set("line", lineData).set("bar", barData);
        return Result.success(res);
    }
}

