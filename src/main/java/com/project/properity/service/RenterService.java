package com.project.properity.service;

import com.project.properity.pojo.Renter;
import com.project.properity.pojo.PageBean;

import java.util.List;

public interface RenterService {

    PageBean page(Integer pageNum, Integer pageSize, String name, String number, String phone);

    void delete(List<Integer> ids);

    void save(Renter renter);

    void update(Renter renter);

    List<Renter> findAll();
}
