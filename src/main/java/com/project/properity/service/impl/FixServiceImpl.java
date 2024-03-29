package com.project.properity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.properity.exception.ServiceException;
import com.project.properity.mapper.FixMapper;
import com.project.properity.pojo.Fix;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.User;
import com.project.properity.service.FixService;
import com.project.properity.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixServiceImpl implements FixService {
    @Autowired
    private FixMapper fixMapper;


    @Override
    public PageInfo<Fix> page(User params) {
        User currentUser = TokenUtils.getCurrentUser();

        if (ObjectUtil.isEmpty(currentUser)) {
            throw new ServiceException("未解析到用户信息,请重新登录");
        }
        if ("用户".equals(currentUser.getRole())){
            params.setId(currentUser.getId());
        }
        PageHelper.startPage(params.getPageNum(), params.getPageSize());
        // 接下来的查询会自动按照当前开启的分页设置来查询
        List<Fix> list = fixMapper.page(params);
        return PageInfo.of(list);
    }

    @Override
    public PageBean list(Integer pageNum, Integer pageSize,String reason) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Fix> fixList = fixMapper.list(reason);
        Page<Fix> p = (Page<Fix>) fixList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        fixMapper.delete(ids);
    }

    @Override
    public void save(Fix fix) {
        fixMapper.insert(fix);
    }

    @Override
    public void update(Fix fix) {
        fixMapper.update(fix);
    }

    @Override
    public List<Fix> findAll() {
        return fixMapper.findAll();
    }

}
