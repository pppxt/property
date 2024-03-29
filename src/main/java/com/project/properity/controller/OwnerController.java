package com.project.properity.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.project.properity.common.AutoLog;
import com.project.properity.exception.ServiceException;
import com.project.properity.pojo.Owner;
import com.project.properity.pojo.PageBean;
import com.project.properity.pojo.Result;
import com.project.properity.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业主管理Controller
 */
@Slf4j
@RestController
@RequestMapping("/owner")
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/page")
    //如果未指定页码，默认为1
    //如果未指定每页记录数，默认为10
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       String name,
                       String number,
                       String phone){
        //调用service分页查询
        PageBean pageBean = ownerService.page(pageNum,pageSize,name,number,phone);
        return Result.success(pageBean);
    }

    //批量删除
    @DeleteMapping("/{ids}")
    @AutoLog("删除业主信息")
    public Result delete(@PathVariable List<Integer> ids){
        ownerService.delete(ids);
        return Result.success();
    }
    //新增
    @PostMapping("/add")
    public Result save(@RequestBody Owner owner){
        ownerService.save(owner);
        return Result.success();
    }
    //更新
    @PutMapping("/update")
    public Result update(@RequestBody Owner owner){
        ownerService.update(owner);
        return Result.success();
    }

    //导出
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws IOException {
        /**
         * 1.查出所有数据
         */
        List<Owner> all = ownerService.findAll();
        if (CollectionUtil.isEmpty(all)){
            throw new ServiceException("未找到数据");
        }
        /**
         * 2、定义一个List来存数据
         */
        //all.size() 给List初始容量
        List<Map<String, Object>> list = new ArrayList<>(all.size());
        for (Owner owner : all){
            Map<String, Object> row = new HashMap<>();
            row.put("所属小区",owner.getAddress());
            row.put("业主姓名",owner.getName());
            row.put("身份证号",owner.getNumber());
            row.put("手机号码",owner.getPhone());
            row.put("性别",owner.getGender());
            row.put("账号状态",owner.getStatus());
            row.put("拥有商铺",owner.getStore());
            row.put("备注",owner.getNotes());
            list.add(row);
        }
        /**
         * 4、将数据写入到Excel文件中
         **/
        //参数 true 表示如果文件不存在，则创建新文件；如果文件已存在，则覆盖现有文件。
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //true表示创建表头
        writer.write(list,true);

        /**
         * 5.设置文件格式 并将其下载下来
         */
        //5.1设置浏览器响应格式,不然不是Excel
        //告诉客户端浏览器，接收到的内容是一个 Excel 文件，并且使用 UTF-8 编码进行解析。
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //告诉客户端浏览器如何处理接收到的内容
        //attachment 表示要求浏览器以附件的形式下载文件
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("业主信息表", "UTF-8") + ".xlsx");

        //5.2获取当前 Servlet 请求的响应对象的输出流，以便向客户端发送数据
        ServletOutputStream outputStream = response.getOutputStream();
        //数据写入到指定的输出流中，并在写入完成后关闭输出流。
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
        List<Owner> infoList = ExcelUtil.getReader(file.getInputStream()).readAll(Owner.class);
        if (!CollectionUtil.isEmpty(infoList)){
            for (Owner owner : infoList){
                try {
                    ownerService.save(owner);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.error("数据批量导入错误");
                }
            }
        }
        return Result.success();
    }
}

