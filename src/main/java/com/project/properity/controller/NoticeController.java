package com.project.properity.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.properity.pojo.*;
import com.project.properity.service.NoticeService;
import com.project.properity.service.UserService;
import com.project.properity.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告
 */
@Slf4j
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @Autowired
    UserService userService;

    @GetMapping("/page")
    //如果未指定页码，默认为1
    //如果未指定每页记录数，默认为10
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String title){
        PageBean pageBean = noticeService.page(pageNum,pageSize,title);
        List<Notice> rows = pageBean.getRows();
        for (Notice row : rows) {
            Integer authorid = row.getUserid();
            User user = userService.selectById(authorid);
            if (user != null) {
                row.setUser(user.getUsername());
            }
        }
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids){
        noticeService.delete(ids);
        return Result.success();
    }

    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Notice notice){
        User currentUser = TokenUtils.getCurrentUser();//获取当前用户信息，即发布人
        notice.setUserid(currentUser.getId()); //发布人id为当前管理员id
        notice.setTime(DateUtil.now()); //当前创建时间，显示发布时间
        noticeService.save(notice);
        return Result.success();
    }

    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Notice notice){
        noticeService.update(notice);
        return Result.success();
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Notice notice = noticeService.selectById(id);
        User user = userService.selectById(notice.getUserid());
        if (user != null) {
            notice.setUser(user.getUsername());
        }
        return Result.success(notice);
    }
    /**
     * 查询公告在首页展示
     */
    @GetMapping("/selectUserData")
    public Result selectUserData() {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<Notice>().orderByDesc("id");
        queryWrapper.eq("open",1); //看到公开的
        //list1首页公告展示 list是公告页面模糊查询
        List<Notice> userList = noticeService.list(queryWrapper);
        return Result.success(userList);
    }

    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Notice> list = noticeService.list(); // 已经查询出表中所有数据
        //次数
        Map<Boolean, Integer> valueCountMap = new HashMap<>();
        // 遍历List并统计字段值出现的次数
        for (Notice notice : list) {
            Boolean fieldValue = notice.getOpen();
            if (valueCountMap.containsKey(fieldValue)) {
                int count = valueCountMap.get(fieldValue);
                valueCountMap.put(fieldValue, count + 1);
            } else {
                valueCountMap.put(fieldValue, 1);
            }
        }
        // 将数据包装为适用于前端的格式，以对象数组的形式存储数据
        List<Dict> lineData = new ArrayList<>();
        List<Dict> barData = new ArrayList<>();
        //entrySet遍历Map的一种方式
        for (Map.Entry<Boolean, Integer> entry : valueCountMap.entrySet()) {
            Dict dataItem = Dict.create().set("open", entry.getKey()).set("value", entry.getValue());
            lineData.add(dataItem); //折线
            barData.add(dataItem); //柱状和饼图
        }

        Dict res = Dict.create().set("line", lineData).set("bar", barData);
        return Result.success(res);
    }
}
