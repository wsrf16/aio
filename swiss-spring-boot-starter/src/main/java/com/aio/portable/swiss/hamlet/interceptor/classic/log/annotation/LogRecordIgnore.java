package com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecordIgnore {
}
