package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import javax.persistence.Transient;

/**
 * 报修服务
 */
@Data
@Getter
@Setter
@TableName(value = "fix")
@NoArgsConstructor
@AllArgsConstructor
public class Fix {
    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String reason;
    private String time;
    private String address;
    private String status;
    private String cause;
    private Integer userId;
    @Transient
    private String userName;

}
