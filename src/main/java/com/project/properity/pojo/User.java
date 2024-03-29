package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户、员工、管理员及分页参数
 */
@Slf4j
@Data
@TableName(value = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String token;
    private String role;
    private String phone;
    private String email;

    //以下不是表中字段，是另外表中封装到这里
    private String name;
    private String reason;
    private Integer pageNum;
    private Integer pageSize;
}
