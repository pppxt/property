package com.project.properity.pojo;


import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 业主实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "owner")
@ToString
@Getter
@Setter
public class Owner {

    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    //导入数据表映射关系
    @Alias("业主姓名")
    private String name;
    @Alias("所属小区")
    private String address;
    @Alias("身份证号")
    private String number;
    @Alias("手机号码")
    private String phone;
    @Alias("性别")
    private String gender;
    @Alias("账号状态")
    private String status;
    @Alias("拥有商铺")
    private Integer store;
    @Alias("备注")
    private String notes;
}
