package com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation;


import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DependsOn
@Inherited
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NetworkProxy {
    @AliasFor(
            annotation = DependsOn.class,
            attribute = "value"
    )
    String[] value() default {"networkProxyBean"};
}
