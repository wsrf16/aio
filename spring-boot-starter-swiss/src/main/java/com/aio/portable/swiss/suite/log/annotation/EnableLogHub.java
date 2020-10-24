package com.aio.portable.swiss.suite.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

//@Import({RedisRepositoriesRegistrar.class})
@DependsOn
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableLogHub {
    @AliasFor(
            annotation = DependsOn.class,
            attribute = "value"
    )
    String[] initialBeanNames() default {};
}
