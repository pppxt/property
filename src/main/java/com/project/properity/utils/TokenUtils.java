package com.project.properity.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.properity.mapper.UserMapper;
import com.project.properity.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class TokenUtils {

    private static UserMapper staticUserMapper;

    @Resource
    private UserMapper userMapper;

    //项目启动时或容器初始化时执行
    //注入之后将其传递给静态变量，就是一个值传递，目的是在静态方法中使用，因为静态方法不能访问非静态成员
    @PostConstruct
    public void setUserService() {
        staticUserMapper = userMapper;
    }

    //生成token
    public static String createToken(String userId, String sign) {
        return JWT.create()
                .withAudience(userId) //保存到token的载荷中
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) //2小时token过期
                .sign(Algorithm.HMAC256(sign)); //以password作为token的密钥（签名），从校验传过来
    }

    //获取当前登录的用户信息
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                return null;
            }
            String userId = JWT.decode(token).getAudience().get(0);
            return staticUserMapper.selectById(Integer.valueOf(userId));
        } catch (Exception e) {
            return null;
        }
    }
}
