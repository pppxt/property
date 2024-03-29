package com.project.properity.exception;

import lombok.Getter;

/**
 * ServiceException类是自定义的运行时异常类，表示服务异常情况。
 */
@Getter //获取code
public class ServiceException extends RuntimeException{

    private final String code;

    public ServiceException(String msg) {
        super(msg);
        this.code = "500";
    }

    public ServiceException(String code, String msg){
        super(msg);
        this.code = code;
    }
}
