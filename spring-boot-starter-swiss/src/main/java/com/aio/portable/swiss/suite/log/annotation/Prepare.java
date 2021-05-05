package com.aio.portable.swiss.suite.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@DependsOn
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Prepare {
    @AliasFor(
            annotation = DependsOn.class,
            attribute = "value"
    )
    String[] value() default {};
}
