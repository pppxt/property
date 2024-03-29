package com.project.properity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 操作日志
 */
@Data
@Getter
@Setter
@TableName(value = "log")
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    @TableId(type = IdType.AUTO) //添加数据后id自动填充
    private Integer id;
    private String name;
    private String time;
    private String username;
    private String ip;

}
