package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.ParkMapper;
import com.project.properity.pojo.Park;
import com.project.properity.pojo.PageBean;
import com.project.properity.service.ParkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkServiceImpl implements ParkService {

    @Autowired
    private ParkMapper parkMapper;


    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String name) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Park> parkList = parkMapper.list(name);
        Page<Park> p = (Page<Park>) parkList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        parkMapper.delete(ids);
    }

    @Override
    public void save(Park park) {
        parkMapper.insert(park);
    }

    @Override
    public void update(Park park) {
        parkMapper.update(park);
    }

    @Override
    public List<Park> findAll() {
        return parkMapper.selectAll();
    }


}
