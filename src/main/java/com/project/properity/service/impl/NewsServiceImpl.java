package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.mapper.NewsMapper;
import com.project.properity.pojo.News;
import com.project.properity.pojo.PageBean;
import com.project.properity.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;


    @Override
    public PageBean page(Integer pageNum, Integer pageSize, String title) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<News> newsList = newsMapper.list(title);
        Page<News> p = (Page<News>) newsList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    public void delete(List<Integer> ids) {
        newsMapper.delete(ids);
    }

    @Override
    public void save(News news) {
        newsMapper.insert(news);
    }

    @Override
    public void update(News news) {
        newsMapper.update(news);
    }

    @Override
    public News selectById(Integer id) {
        return newsMapper.selectById(id);
    }
}
