package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 新闻管理、详情
 */
@Data
public class News {
    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String title;
    private String description;
    private String content;
    private Integer authorid;
    private String time;

    //注解表示字段不在数据库中，而是业务处理用的
    @TableField(exist = false)
    private String author;
}
