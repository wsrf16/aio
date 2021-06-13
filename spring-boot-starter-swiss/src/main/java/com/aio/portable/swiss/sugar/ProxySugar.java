package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
//import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProxySugar {
    public static <T> T cglibProxy(Class<T> clazz, MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(methodInterceptor);
        return (T) enhancer.create();
    }

    public static <T> T cglibProxy(Class<T> clazz, MethodInterceptor... methodInterceptors) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallbacks(methodInterceptors);
        return (T) enhancer.create();
    }


    public static <T> T jdkProxy(Class<T> clazz, InvocationHandler handler) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = ClassSugar.collectSuperInterfaces(clazz);
        T proxy = (T) Proxy.newProxyInstance(
                classLoader,
                interfaces,
                handler);
        return proxy;
    }

    public static <T> T proxyFactory(Object target, Advice... advices) {
        ProxyFactory proxyFactory = new ProxyFactory(target);
        final Class<?>[] interfaces = ClassSugar.collectSuperInterfaces(target.getClass());
//        proxyFactory.setInterfaces(interfaces);
        proxyFactory.setOptimize(true);
//        proxyFactory.setTarget(target);
//        proxyFactory.setTargetClass(target.getClass());
//        proxyFactory.setProxyTargetClass(true);
        CollectionSugar.toList(advices)
                .stream()
                .forEach(c -> proxyFactory.addAdvice(c));
        return (T) proxyFactory.getProxy();
    }

    public static boolean isJdkProxy(@Nullable Object object) {
        return object instanceof SpringProxy && Proxy.isProxyClass(object.getClass());
    }

    public static boolean isCglibProxy(@Nullable Object object) {
        return object instanceof SpringProxy && ClassUtils.isCglibProxy(object);
    }
/*
    {
        ErPrinterImpl erPrinter = new ErPrinterImpl();
        ErPrinter proxy = ProxySugar.jdkProxy(
                ErPrinterImpl.class,
                (_proxy, _method, _args) -> {
                    System.out.println("before invoke()333!!!!");
                    Object invoke = _method.invoke(erPrinter);
                    System.out.println("after invoke()333!!!!");
                    return invoke;
                });
        proxy.out();
    }
    */
}


