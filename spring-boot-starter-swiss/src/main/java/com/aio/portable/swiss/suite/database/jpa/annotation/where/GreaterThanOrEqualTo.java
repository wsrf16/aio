package com.aio.portable.swiss.suite.database.jpa.annotation.where;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThanOrEqualTo {
    String targetProperty();
}
