package com.project.properity.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //配置jwt的拦截器规则
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register","/owner/import","/alipay/**","/api/**");
        super.addInterceptors(registry);
    }

    // 定义一个JWT拦截器，并将其注册到拦截器注册表中
    @Bean
    public HandlerInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}
