package com.project.properity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    public static final String CODE_SUCCESS ="200";
    public static final String CODE_AUTH_ERROR ="401"; //未经授权,即权限错误
    public static final String CODE_SYS_ERROR ="500"; //内部服务器错误

    private String code;//响应码，1 代表成功; 0 代表失败
    private String msg;  //响应信息 描述字符串
    private Object data; //返回的数据

    //成功响应
    public static Result success(){
        return new Result(CODE_SUCCESS,"Success",null);
    }
    //查询 成功响应
    public static Result success(Object data){
        return new Result(CODE_SUCCESS,"Success",data);
    }
    //失败响应
    public static Result error(String msg){
        return new Result(CODE_SYS_ERROR,msg,null);
    }
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
    public static Result error(){
        return new Result(CODE_SYS_ERROR,"系统错误",null);
    }

}
