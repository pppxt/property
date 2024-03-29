package com.project.properity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.NoticeMapper;
import com.project.properity.pojo.Notice;
import com.project.properity.pojo.PageBean;
import com.project.properity.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String title) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<Notice> noticeList = noticeMapper.list(title);
        Page<Notice> p = (Page<Notice>) noticeList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        noticeMapper.delete(ids);
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.update(notice);
    }

    @Override
    public Notice selectById(Integer id) {
        return noticeMapper.selectById(id);
    }

}
