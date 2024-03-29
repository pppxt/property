package com.project.properity.controller;

import com.project.properity.mapper.GoodsMapper;
import com.project.properity.mapper.OrdersMapper;
import com.project.properity.pojo.Goods;
import com.project.properity.pojo.Orders;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api")
@MapperScan("com.project.properity.mapper")
@CrossOrigin(origins = "*")
public class ApiController {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private OrdersMapper ordersMapper;

    @GetMapping("/goods")
    public List<Goods> getGoods() {
        return goodsMapper.selectList(null);
    }

    @GetMapping("/orders")
    public List<Orders> getOrders() {
        return ordersMapper.selectList(null);
    }

    @Transactional
    @PostMapping("/buy")
    public boolean buy(@RequestParam Integer goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        int store = goods.getStore() - 1;
        if (store < 0) {
            return false;
        }
        Date date = new Date();
        Orders orders = new Orders();
        orders.setGoodsId(goodsId);
        orders.setCreateTime(date);
        orders.setName(goods.getName() + "订单");
        orders.setOrderId(new SimpleDateFormat("yyyyMMdd").format(date) + System.currentTimeMillis());
        orders.setTotal(goods.getPrice().multiply(BigDecimal.ONE));

        goods.setStore(store);
        return ordersMapper.insert(orders) > 0 &&  goodsMapper.updateById(goods) > 0;
    }
}
