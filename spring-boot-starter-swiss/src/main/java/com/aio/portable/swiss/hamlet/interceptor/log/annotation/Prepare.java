package com.aio.portable.swiss.hamlet.interceptor.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@DependsOn
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@interface Prepare {
    @AliasFor(
            annotation = DependsOn.class,
            attribute = "value"
    )
    String[] value() default {};
}
