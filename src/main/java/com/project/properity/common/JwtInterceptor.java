package com.project.properity.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.properity.exception.ServiceException;
import com.project.properity.mapper.UserMapper;
import com.project.properity.pojo.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截校验
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    //请求到达目标方法之前执行
    /**
     * 参数1：所有请求信息
     * 参数2：响应信息
     * 参数3：处理请求的处理器
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /**
         * 1、拿到token,从http header里面传过来的参数
         */
        //写这个类之前，token已生成并且通过UserServiceImpl生成了user的token返回给了前端，因此直接从header中拿
        String token = request.getHeader("token");
        if(StrUtil.isBlank(token)) {
            //获取http请求中的参数，例如url参数 ？token=xxxx中的token
            token = request.getParameter("token");
        }
        //这里是排除拦截的注解实现方式
        // 判断前者类型是否是后者
        if (handler instanceof HandlerMethod) {
            //获取方法上的指定注解信息
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        /**
         * 2.验证token是否存在
         */
        if(StrUtil.isBlank(token)) {
            throw new ServiceException("401","无token,请重新登录");
        }

        /**
         * 3、获取token中的user
         */
        String userId;
        try{
            //JWT.decode(token)对token解码，getAudience得到载荷信息 这里的载荷包含userId，get 0 为第一个数据
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j){
            throw new ServiceException("401","token验证失败,请重新登录");
        }
        /**
         * 4、根据token中的user 查询数据库
         */
        User user = userMapper.selectById(Integer.valueOf(userId));
        if(user ==null){
            throw new ServiceException("401","用户不存在,请重新登录");
        }

        /**
         * 5、用户正确了之后拿密码，通过用户密码加密之后  生成一个验证器
         */
        //require() 方法设置了算法和密钥,传入了用户的密码作为密钥
        //这样，在验证 JWT 令牌时，会使用用户的密码进行解密操作。
        //调用 build() 方法即可创建一个 JWTVerifier 对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            /**
             * 6、验证token
             */
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException("401","请登录");
        }
        return true;
    }
}
