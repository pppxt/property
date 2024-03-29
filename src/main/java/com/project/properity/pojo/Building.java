package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.persistence.*;

/**
 * 楼栋管理
 */
@Data
@Getter
@Setter
@TableName(value = "building")
public class Building {

    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    //楼栋名
    private String name;
    //小区名
    @Transient
    private String comName;
    private Integer typeId;
}
