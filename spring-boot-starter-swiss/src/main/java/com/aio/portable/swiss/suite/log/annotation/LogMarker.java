package com.aio.portable.swiss.suite.log.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMarker {
}
