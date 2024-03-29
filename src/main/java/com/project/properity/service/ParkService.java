package com.project.properity.service;

import com.project.properity.pojo.Park;
import com.project.properity.pojo.PageBean;

import java.util.List;

public interface ParkService {

    PageBean page(Integer pageNum, Integer pageSize, String name);

    void delete(List<Integer> ids);

    void save(Park park);

    void update(Park park);

    List<Park> findAll();
}
