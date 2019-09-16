package com.aio.portable.swiss.data.jpa.annotation.order;

import org.springframework.data.domain.Sort;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    String targetProperty();

    Sort.Direction direction() default Sort.Direction.ASC;

    int priority();
}
