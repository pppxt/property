package com.project.properity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.project.properity.pojo.Notice;
import com.project.properity.pojo.PageBean;

import java.util.List;

public interface NoticeService extends IService<Notice> {

    PageBean page(Integer pageNum, Integer pageSize, String title);

    void delete(List<Integer> ids);

    void update(Notice notice);

    Notice selectById(Integer id);

}
