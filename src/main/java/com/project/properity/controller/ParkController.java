package com.project.properity.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.project.properity.pojo.*;
import com.project.properity.service.ParkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 车位
 */
@Slf4j
@RestController
@RequestMapping("/park")
public class ParkController {

    @Autowired
    ParkService parkService;

    @GetMapping("/page")
    //如果未指定页码，默认为1
    //如果未指定每页记录数，默认为10
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String name){
        //调用service分页查询
        PageBean pageBean = parkService.page(pageNum,pageSize,name);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        parkService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Park park){
        parkService.save(park);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Park park){
        park.setDate(DateUtil.now()); //当前创建时间，显示发布时间
        parkService.update(park);
        return Result.success();
    }
    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Park> list = parkService.findAll();
        // 折线图数据
        // 从车位数据中取不重复的地址
        //.map(Park::getName)获取Park对象的名称
        //.collect(Collectors.toSet())// 将名称收集到Set集合中
        Set<String> names = list.stream().map(Park::getName).collect(Collectors.toSet());
        // 将 names 转换为 ArrayList
        List<String> nameList = CollUtil.newArrayList(names);
        nameList.sort(Comparator.naturalOrder()); // 对列表按自然顺序排序
        List<Dict> lineList = new ArrayList<>(); // 用于存储折线图数据的列表,小区和对应车位数量
        List<Dict> barList4 = new ArrayList<>(); // 用于存储柱状和饼图数据的列表,小区和对应车位数量
        for (String name : nameList) {
            // 计算具有相同地址的数量总和
            //map方法对流中的元素进行映射转换，Park::getNumber 表示获取每个元素 Park 的车位数量
            //reduce方法将流中的元素进行归约操作，BigDecimal::add 表示对流中的元素进行累加操作
            //orElse方法用于获取归约操作的结果，如果流为空则返回默认值
            BigDecimal sum = list.stream().filter(parks -> parks.getName().equals(name)).map(Park::getNumber)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            //存储名称和对应的总和
            Dict dict = Dict.create();
            Dict dataItem = dict.set("name", name).set("value", sum);
            lineList.add(dataItem);
            barList4.add(dataItem);
        }

        // 各小区收费单价柱状图数据
        List<Dict> barList2 = new ArrayList<>();
        Set<String> names2  = list.stream().map(Park::getName).collect(Collectors.toSet());
        for (String name2 : names2) {
            // 统计当前日期的所有金额总数和
            BigDecimal sum2 = list.stream().filter(parks -> parks.getName().equals(name2)).map(Park::getMoney)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict = Dict.create();
            Dict bar2 = dict.set("name2", name2).set("value2", sum2);
            barList2.add(bar2);
        }

        // 根据日期查询车次柱状图数据
        List<Dict> barList1 = new ArrayList<>();
        Set<String> dates = list.stream().map(Park::getDate).collect(Collectors.toSet());
        List<String> dateList = CollUtil.newArrayList(dates);
        dateList.sort(Comparator.naturalOrder()); // 对列表按自然顺序排序
        for (String date : dateList) {
            // 统计当前日期的所有金额总数和
            BigDecimal sum1 = list.stream().filter(parks -> parks.getDate().equals(date)).map(Park::getTrips)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict1 = Dict.create();
            Dict bar1 = dict1.set("date", date).set("value", sum1);
            barList1.add(bar1);
        }

        // 包装所有数据
        Dict res = Dict.create().set("line", lineList).set("bar1", barList1).set("bar", barList4).set("bar2", barList2);
        return Result.success(res);
    }

}
