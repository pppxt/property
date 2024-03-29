package com.project.properity.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.properity.exception.ServiceException;
import com.project.properity.pojo.Community;
import com.project.properity.pojo.Owner;
import com.project.properity.pojo.Renter;
import com.project.properity.pojo.Result;
import com.project.properity.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小区管理
 */
@Slf4j
@RestController
@RequestMapping("/community")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    //查询所有数据
    @GetMapping
    public Result findAll() {
        return Result.success(communityService.list());
    }
    //新增修改数据
    @PostMapping
    public Result save(@RequestBody Community community){
        communityService.saveCommunity(community);
        return Result.success();
    }

    //删除数据
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        communityService.removeById(id);
        return Result.success();
    }

    //删除数据
    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        communityService.removeByIds(ids);
        return Result.success();
    }

    //分页查询mybatis-plus
    //RequestParam注解接收 ?pageNum=1&pageSize=10
    @GetMapping("/page")
    public IPage<Community> findPage(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(defaultValue = "") String name,
                                     @RequestParam(defaultValue = "") String address,
                                     @RequestParam(defaultValue = "") String genre) {
        IPage<Community> page = new Page<>(pageNum,pageSize);
        QueryWrapper<Community> queryWrapper = new QueryWrapper<>();
        //当查询字段不为空，其余有空时也要查到if语句
        if (!"".equals(name)){
            queryWrapper.like("name",name);
        }
        if (!"".equals(address)){
            queryWrapper.like("address",address);
        }
        if (!"".equals(genre)){
            queryWrapper.like("genre",genre);
        }
        //倒叙显示最新添加的数据
        queryWrapper.orderByDesc("id");
        return communityService.page(page,queryWrapper);
    }

    //导出
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {

        //1、从数据库中查询出所有数据
        List<Community> all = communityService.list();
        if (CollectionUtil.isEmpty(all)){
            throw new ServiceException("未找到数据");
        }
        //2、定义一个List来存处理之后的数据，用于放到list中
        List<Map<String, Object>> list = new ArrayList<>(all.size()); //给一个空间
        //3、定义Map遍历每一条数据，然后封装到map中
        for (Community community : all){
            Map<String, Object> row = new HashMap<>();
            row.put("小区名称",community.getName());
            row.put("小区地址",community.getAddress());
            row.put("小区类型",community.getGenre());
            row.put("开发商",community.getDeveloper());
            row.put("房屋数量",community.getQuantity());
            row.put("小区规模",community.getArea());
            list.add(row);
        }
        //4、创建一个ExcelWriter,把数据用这个writer生成出来
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list,true);
        //5、把这个excel下载下来
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("小区信息表", "UTF-8") + ".xlsx");
        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream, true);
        writer.close();
        IoUtil.close(System.out);

        return Result.success();
    }
    /**
     * 批量导入
     * @param file 传入的excel文件对象
     * @return 导入结果
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        List<Community> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Community.class);
        if (!CollectionUtil.isEmpty(infoList)){
            for (Community community : infoList){
                try {
                    communityService.save(community);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.error("数据批量导入错误");
                }
            }
        }
        return Result.success();
    }

    /**
     * 获取统计图数据
     * @return 动态数据
     */
    @GetMapping("/charts")
    public Result charts() {
        List<Community> list = communityService.list(); // 已经查询出表中所有数据

        //次数
        Map<String, Integer> valueCountMap = new HashMap<>();
        // 遍历List并统计字段值出现的次数
        for (Community community : list) {
            String fieldValue = community.getGenre();
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

        for (Map.Entry<String, Integer> entry : valueCountMap.entrySet()) {
            Dict dataItem = Dict.create().set("genre", entry.getKey()).set("value", entry.getValue());
            lineData.add(dataItem); //折线
            barData.add(dataItem); //柱状和饼图
        }

        Dict res = Dict.create().set("line", lineData).set("bar", barData);
        return Result.success(res);
    }
}
