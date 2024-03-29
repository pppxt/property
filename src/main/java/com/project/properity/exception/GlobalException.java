package com.project.properity.exception;

import com.project.properity.pojo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 标记为全局异常处理类
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(ServiceException.class)  // 处理ServiceException类型的异常
    @ResponseBody // 将返回结果以JSON格式返回给客户端
    public Result serviceException(ServiceException e) {
        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e) {
        e.printStackTrace();
        return Result.error("500", "系统错误");
    }
}
