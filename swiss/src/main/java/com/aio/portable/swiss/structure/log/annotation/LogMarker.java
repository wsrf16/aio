package com.aio.portable.swiss.structure.log.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
public @interface LogMarker {
}
