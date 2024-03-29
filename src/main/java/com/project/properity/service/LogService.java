package com.project.properity.service;

import com.github.pagehelper.PageInfo;
import com.project.properity.pojo.Log;
import com.project.properity.pojo.User;

import java.util.List;

public interface LogService {

    //分页查询
    PageInfo page(User params);

    //批量删除
    void delete(List<Integer> ids);

    //新增
    void save(Log log);

}
