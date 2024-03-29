package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.OwnerMapper;
import com.project.properity.pojo.Owner;
import com.project.properity.pojo.PageBean;
import com.project.properity.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerMapper ownerMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize,String name, String number, String phone) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Owner> ownerList = ownerMapper.list(name, number, phone);
        Page<Owner> p = (Page<Owner>) ownerList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        ownerMapper.delete(ids);
    }

    @Override
    public void save(Owner owner) {
        ownerMapper.insert(owner);
    }

    @Override
    public void update(Owner owner) {
        ownerMapper.update(owner);
    }

    @Override
    public List<Owner> findAll() {
        return ownerMapper.selectAll();
    }

}
