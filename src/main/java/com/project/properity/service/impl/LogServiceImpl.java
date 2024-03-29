package com.project.properity.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.properity.mapper.LogMapper;
import com.project.properity.pojo.Log;
import com.project.properity.pojo.User;
import com.project.properity.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;


    @Override
    public PageInfo<Log> page(User params) {

        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Log> list = logMapper.page(params);
        return PageInfo.of(list);
    }

    @Override
    public void delete(List<Integer> ids) {
        logMapper.delete(ids);
    }

    @Override
    public void save(Log log) {
        logMapper.insert(log);
    }

}
