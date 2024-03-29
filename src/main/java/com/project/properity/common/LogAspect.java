package com.project.properity.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.project.properity.pojo.Log;
import com.project.properity.pojo.Result;
import com.project.properity.pojo.User;
import com.project.properity.service.LogService;
import com.project.properity.utils.TokenUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 处理切面的“监控”
 */
@Component
@Aspect
public class LogAspect {

    @Resource
    private LogService logService;
    //围绕切面，拦截被 @autoLog 注解标记的方法
    @Around("@annotation(autoLog)")
    //参数表示连接点和自定义注解
    public Object doAround(ProceedingJoinPoint joinPoint, AutoLog autoLog) throws Throwable {
        /**
         * 1.获取操作信息
         */
        // 操作内容，在注解里已经定义了value()，然后在需要切入的接口上面去写上对应的操作内容
        String name = autoLog.value();
        // 操作时间（当前时间）
        String time = DateUtil.now();
        // 操作人
        String username = "";
        User user = TokenUtils.getCurrentUser();
        if (ObjectUtil.isNotNull(user)) {
            username = user.getUsername();
        }
        // 操作人IP
        //1 RequestContextHolder.getRequestAttributes()获取当前请求对象属性
        //2 RequestAttributes对象先转为ServletRequestAttributes 对象
        //3 调用 getRequest() 方法就能获取到当前的 HTTP 请求对象 HttpServletRequest
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();
        /**
         * 2.执行被拦截的方法
         */
        Result result = (Result) joinPoint.proceed();
        /**
         * 3.获取操作内容返回结果的username
         * 虽然通过 TokenUtils.getCurrentUser() 可以获取操作人的信息，但是在实际执行接口并获取结果时
         * 可能需要根据具体情况来获取正确的操作人信息，因此进行了类型转换以确保获取到正确的操作人 username。
         */
        Object data = result.getData();
        //instanceof 关键字，可以在运行时判断对象的类型
        //检查 data 是否是 User 类型的实例
        if (data instanceof User) {
            User admin = (User) data;
            username = admin.getUsername();
        }
        /**
         * 4.添加日志到记录中
         */
        Log log = new Log(null, name, time, username, ip);
        logService.save(log);
        return result;
    }
}
