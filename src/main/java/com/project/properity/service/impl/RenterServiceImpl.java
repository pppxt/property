package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.RenterMapper;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Renter;
import com.project.properity.service.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RenterServiceImpl implements RenterService {

    @Autowired
    private RenterMapper renterMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize,String name, String number, String phone) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Renter> renterList = renterMapper.list(name, number, phone);
        Page<Renter> p = (Page<Renter>) renterList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        renterMapper.delete(ids);
    }

    @Override
    public void save(Renter renter) {
        renterMapper.insert(renter);
    }

    @Override
    public void update(Renter renter) {
        renterMapper.update(renter);
    }

    @Override
    public List<Renter> findAll() {
        return renterMapper.findAll();
    }

}
