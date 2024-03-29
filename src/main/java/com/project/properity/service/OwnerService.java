package com.project.properity.service;

import com.project.properity.pojo.Owner;
import com.project.properity.pojo.PageBean;

import java.util.List;

/**
 * 业主管理
 */
public interface OwnerService {

    //分页查询
    PageBean page(Integer pageNum, Integer pageSize, String name, String number, String phone);

    //批量删除
    void delete(List<Integer> ids);

    //新增
    void save(Owner owner);

    //更新
    void update(Owner owner);

    //查询所有
    List<Owner> findAll();
}
