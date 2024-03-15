package com.aio.portable.swiss.hamlet.interceptor.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {
    boolean ignore() default true;
}
