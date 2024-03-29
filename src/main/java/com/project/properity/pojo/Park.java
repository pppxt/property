package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 车位
 */
@Data
public class Park {
    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String name;
    private BigDecimal number;
    private String category;
    private BigDecimal money;
    private String date;
    private BigDecimal trips;

    //注解表示字段不在数据库中，而是业务处理用的
    @TableField(exist = false)
    private String user;
}
