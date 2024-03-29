package com.project.properity.service;

import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.User;

public interface UserService {
    /**
     * 管理员登录操作
     * @param user
     * @return
     */
    User login(User user);

    User register(User user);

    User selectById(Integer id);

    void update(User user);

    void resetPassword(User user);

    PageBean page(Integer pageNum, Integer pageSize, String username);
}
