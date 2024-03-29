package com.project.properity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.properity.mapper.GoodsMapper;
import com.project.properity.pojo.Goods;
import com.project.properity.service.GoodsService;
import org.springframework.stereotype.Service;

//加上rollbackFor=Exception.class,可以让事物在遇到非运行异常时也回滚。
//@Transactional(rollbackFor=Exception.class)
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

}

