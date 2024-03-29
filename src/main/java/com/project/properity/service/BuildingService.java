package com.project.properity.service;

import com.project.properity.pojo.Building;
import com.project.properity.pojo.PageBean;

import java.util.List;

public interface BuildingService {

    //分页查询
    PageBean page(Integer pageNum, Integer pageSize, String name, String comName);

    //批量删除
    void delete(List<Integer> ids);

    //新增
    void save(Building building);

    //更新
    void update(Building building);
}
