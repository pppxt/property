package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.BuildingMapper;
import com.project.properity.pojo.Building;
import com.project.properity.pojo.PageBean;
import com.project.properity.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String name, String comName) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Building> buildingList = buildingMapper.list(name, comName);
        Page<Building> p = (Page<Building>) buildingList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        buildingMapper.delete(ids);
    }

    @Override
    public void save(Building building) {
        buildingMapper.insert(building);
    }

    @Override
    public void update(Building building) {
        buildingMapper.update(building);
    }

}
