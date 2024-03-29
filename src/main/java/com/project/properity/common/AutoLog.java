package com.project.properity.common;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoLog {
    //注解在方法上时,默认值为空
    String value() default "";
}
