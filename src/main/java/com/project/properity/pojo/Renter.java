package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


/**
 * 租户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
//数据库名与实体类名不一致或不符合驼峰命名时指定表名，不加会自动寻找db中的小写
@TableName(value = "renter")
@Getter
@Setter
public class Renter {

    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String name;
    private String address;
    private String number;
    private String phone;
    private String gender;
}
