package com.aio.portable.swiss.suite.log.facade;

import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@FunctionalInterface
public interface InterceptMethod {
    Object intercept(LogHub logHub, Object _proxy, Method _method, Object[] _args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
