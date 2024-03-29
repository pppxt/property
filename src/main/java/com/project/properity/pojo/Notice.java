package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 公告
 */
@Data
public class Notice {
    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String title;
    private String content;
    private Integer userid;
    private String time;
    private Boolean open;

    //注解表示字段不在数据库中，而是业务处理用的
    @TableField(exist = false)
    private String user;
}
