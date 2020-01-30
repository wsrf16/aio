package com.aio.portable.swiss.structure.log.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMarkerExcept {
}
