package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * 小区实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "community")
@ToString
public class Community {

    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String name;
    private String address;
    private String genre;
    private String developer;
    private Date era; //创建时间
    private Integer quantity;
    private String area;

}
