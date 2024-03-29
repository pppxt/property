package com.project.properity.controller;

import cn.hutool.core.date.DateUtil;
import com.project.properity.pojo.News;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Result;
import com.project.properity.pojo.User;
import com.project.properity.service.NewsService;
import com.project.properity.service.UserService;
import com.project.properity.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻
 */
@Slf4j
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;

    @GetMapping("/page")
    //如果未指定页码，默认为1
    //如果未指定每页记录数，默认为10
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String title){
        PageBean pageBean = newsService.page(pageNum,pageSize,title);
        List<News> rows = pageBean.getRows();
        for (News row : rows) {
            Integer authorid = row.getAuthorid();
            User user = userService.selectById(authorid);
            if (user != null) {
                row.setAuthor(user.getUsername());
            }
        }
        return Result.success(pageBean);
    }
    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        newsService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody News news){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户信息，即发布人
        news.setAuthorid(currentUser.getId()); //发布人id为当前管理员id
        news.setTime(DateUtil.now()); //当前创建时间，显示发布时间
        newsService.save(news);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody News news){
        //log.info("更新信息 : {}", news);
        newsService.update(news);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        News news = newsService.selectById(id);
        User user = userService.selectById(news.getAuthorid());
        if (user != null) {
            news.setAuthor(user.getUsername());
        }
        return Result.success(news);
    }
}
