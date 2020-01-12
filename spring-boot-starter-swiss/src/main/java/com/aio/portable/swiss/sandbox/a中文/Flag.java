package com.aio.portable.swiss.sandbox.a中文;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Flag {
    boolean isMaster() default false;
    @AliasFor("age")
    int age() default 18;
}