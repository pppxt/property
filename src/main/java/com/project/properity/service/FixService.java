package com.project.properity.service;

import com.github.pagehelper.PageInfo;
import com.project.properity.pojo.Fix;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.User;

import java.util.List;

public interface FixService {

    //分页查询
    PageInfo page(User params);

    PageBean list(Integer pageNum, Integer pageSize, String reason);

    //批量删除
    void delete(List<Integer> ids);

    //新增
    void save(Fix fix);

    //更新
    void update(Fix fix);

    //查询所有
    List<Fix> findAll();
}
