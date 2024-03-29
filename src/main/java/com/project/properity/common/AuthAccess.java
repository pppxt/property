package com.project.properity.common;

import java.lang.annotation.*;

//自定义注解，用于排除在拦截之外
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
}
