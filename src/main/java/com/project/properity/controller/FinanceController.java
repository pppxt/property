package com.project.properity.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Dict;
import com.project.properity.pojo.Goods;
import com.project.properity.pojo.Result;
import com.project.properity.service.GoodsService;
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
 * 财务图
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class FinanceController {

    @Autowired
    GoodsService goodsService;

    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Goods> list = goodsService.list();
        // 折线图数据
        // 从车位数据中取不重复的地址
        Set<String> names = list.stream().map(Goods::getName).collect(Collectors.toSet());
        List<String> nameList = CollUtil.newArrayList(names);
        nameList.sort(Comparator.naturalOrder()); // 对列表按自然顺序排序
        List<Dict> lineList = new ArrayList<>(); // 用于存储折线图数据的列表
        for (String name : nameList) {
            // 计算具有相同地址的数量总和
            BigDecimal sum = list.stream().filter(goods -> goods.getName().equals(name)).map(Goods::getPrice)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            //存储名称和对应的总和
            Dict dict = Dict.create();
            Dict line = dict.set("name", name).set("value", sum);
            lineList.add(line);
        }

        // 各小区收费单价柱状图数据
        List<Dict> barList = new ArrayList<>();
        Set<String> names1  = list.stream().map(Goods::getName).collect(Collectors.toSet());
        for (String name : names1) {
            // 统计当前日期的所有金额总数和
            BigDecimal sum = list.stream().filter(goods -> goods.getName().equals(name)).map(Goods::getPrice)
                    .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
            Dict dict = Dict.create();
            Dict bar = dict.set("name", name).set("value", sum);
            barList.add(bar);
        }

        // 包装所有数据
        Dict res = Dict.create().set("line", lineList).set("bar", barList);
        return Result.success(res);
    }

}
