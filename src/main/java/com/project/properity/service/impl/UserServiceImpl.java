package com.project.properity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.project.properity.exception.ServiceException;
import com.project.properity.mapper.UserMapper;
import com.project.properity.pojo.Owner;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.User;
import com.project.properity.service.UserService;
import com.project.properity.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //验证用户账户是否合法
    @Override
    public User login(User user) {
        //方法名命名不用login因为是业务方法名，而mapper接口是持久层
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null){
            //抛出一个自定义异常
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }
    //注册
    @Override
    public User register(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser != null){
            //抛出一个自定义异常
            throw new ServiceException("用户名已存在");
        }
        userMapper.insert(user);
        return user;
    }
    //根据id查询
    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }
    //更改个人信息
    @Override
    public void update(User user) {
        userMapper.update(user);
    }
    //
    public void resetPassword(User user) {
        User dbUser = userMapper.selectByUsername(user.getUsername());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new ServiceException("验证错误");
        }
        dbUser.setPassword("123");   // 重置密码
        userMapper.update(dbUser);
    }

    @Override
    public PageBean page(Integer pageNum, Integer pageSize,String username) {
        //1. 设置分页参数
        PageHelper.startPage(pageNum,pageSize);

        //2. 执行查询
        List<User> userList = userMapper.list(username);
        Page<User> p = (Page<User>) userList;

        //3. 封装PageBean对象
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

}
