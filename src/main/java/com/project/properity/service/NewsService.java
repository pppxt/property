package com.project.properity.service;

import com.project.properity.pojo.News;
import com.project.properity.pojo.PageBean;

import java.util.List;

public interface NewsService {

    PageBean page(Integer pageNum, Integer pageSize, String title);

    void delete(List<Integer> ids);

    void save(News news);

    void update(News news);

    News selectById(Integer id);
}
