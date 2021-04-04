package com.aio.portable.swiss.suite.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@DependsOn
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InitialLogProperties {
    @AliasFor(
            annotation = DependsOn.class,
            attribute = "value"
    )
    String[] initialBeanNames() default {};

//    default void ffff() {
//        com.aio.portable.swiss.suite.log.annotation.InitialLogProperties
//    }
}
