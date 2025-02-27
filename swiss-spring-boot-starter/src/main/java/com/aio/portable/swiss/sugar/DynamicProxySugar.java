package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import org.aopalliance.aop.Advice;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.function.Consumer;

//import org.springframework.cglib.proxy.InvocationHandler;
//import org.springframework.cglib.proxy.Proxy;

public class DynamicProxySugar {
    // JdkDynamicAopProxy
    // DefaultAopProxyFactory
    public static final <T> T cglibProxy(Class<T> clazz, MethodInterceptor methodInterceptor) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(methodInterceptor);
        return (T) enhancer.create();
    }

    public static final <T> T cglibProxy(Class<T> clazz, MethodInterceptor methodInterceptor, Consumer<Enhancer> enhancerProcess) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(methodInterceptor);
        enhancerProcess.accept(enhancer);
        return (T) enhancer.create();
    }

    public static final <T> T cglibProxy(Class<T> clazz, MethodInterceptor[] methodInterceptors) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallbacks(methodInterceptors);
        return (T) enhancer.create();
    }

    public static final <T> T cglibProxy(Class<T> clazz, MethodInterceptor[] methodInterceptors, Consumer<Enhancer> enhancerProcess) {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterceptDuringConstruction(false);
        enhancer.setSuperclass(clazz);
        enhancer.setCallbacks(methodInterceptors);
        enhancerProcess.accept(enhancer);
        return (T) enhancer.create();
    }


    public static final <T> T jdkProxy(Class<T> clazz, InvocationHandler handler) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] interfaces = ClassSugar.collectSuperInterfaces(clazz);
        T proxy = (T) Proxy.newProxyInstance(
                classLoader,
                interfaces,
                handler);
        return proxy;
    }

    public static final <T> T proxyFactory(Object target, Advice... advices) {
        ProxyFactory proxyFactory = new ProxyFactory(target);
//        Class<?>[] interfaces = ClassSugar.collectSuperInterfaces(target.getClass());
//        proxyFactory.setInterfaces(interfaces);
        proxyFactory.setOptimize(true);
//        proxyFactory.setTarget(target);
//        proxyFactory.setTargetClass(target.getClass());
//        proxyFactory.setProxyTargetClass(true);
        CollectionSugar.asList(advices)
                .stream()
                .forEach(c -> proxyFactory.addAdvice(c));
        return (T) proxyFactory.getProxy();
    }

    public static final <T> T proxyFactory(Object target, Class<T> interfaceClazz, Advice... advices) {
        ProxyFactory proxyFactory = new ProxyFactory(target);
        Class<?>[] interfaces = ClassSugar.collectSuperInterfaces(interfaceClazz);
        // 只有设置了interfaces和optimize=true时，才会生成jdkProxy，否则cglibProxy
//        proxyFactory.setInterfaces(interfaces);
        proxyFactory.setOptimize(true);
//        proxyFactory.setTarget(target);
//        proxyFactory.setTargetClass(target.getClass());
//        proxyFactory.setProxyTargetClass(true);
        CollectionSugar.asList(advices)
                .stream()
                .forEach(c -> proxyFactory.addAdvice(c));
        return (T) proxyFactory.getProxy();
    }

    public static final boolean isJdkProxy(@Nullable Object object) {
//        return object instanceof SpringProxy && Proxy.isProxyClass(object.getClass());
        return Proxy.isProxyClass(object.getClass());
    }

    public static final boolean isCglibProxy(@Nullable Object object) {
//        return object instanceof SpringProxy && ClassUtils.isCglibProxy(object);
        return ClassUtils.isCglibProxy(object);
    }

    public static final Class<?> getUserClass(Class<?> clazz) {
        return ClassUtils.getUserClass(clazz);
    }

    public static final <T> T unpackCglib(Object source, Class<T> clazz) {
        return (T)DeepCloneSugar.Json.clone(DeepCloneSugar.Cglib.clone(source), clazz);
    }

    public static final <T> T unpackCglib(Object source) {
        return (T)DeepCloneSugar.Json.clone(DeepCloneSugar.Cglib.clone(source), ClassUtils.getUserClass(source));
    }

    public static final <T> T getAopTargetObject(T proxy) {
        T t = null;
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            t = getAopJdkTargetObject(proxy);
        } else if (AopUtils.isCglibProxy(proxy)) {
            t = getAopCglibTargetObject(proxy);
        }
        return t;
    }

    private static final <T> T getAopJdkTargetObject(T proxy)  {
        try {
//            Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
//            h.setAccessible(true);
//            AopProxy aopProxy = (AopProxy) h.get(proxy);
            AopProxy aopProxy = (AopProxy) ClassSugar.Fields.getDeclaredFieldValue(proxy, proxy.getClass().getSuperclass(), "h");

//            Field advised = aopProxy.getClass().getDeclaredField("advised");
//            advised.setAccessible(true);
//            T target = (T) ((AdvisedSupport) advised.get(aopProxy)).getTargetSource().getTarget();
            AdvisedSupport advisedSupport = ClassSugar.Fields.getDeclaredFieldValue(aopProxy, "advised");
            T target = (T) advisedSupport.getTargetSource().getTarget();
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T getAopCglibTargetObject(T proxy) {
        try {
//            Field h = beanInstance.getClass().getDeclaredField("CGLIB$CALLBACK_0");
//            h.setAccessible(true);
//            Object dynamicAdvisedInterceptor = h.get(beanInstance);
            Object dynamicAdvisedInterceptor = ClassSugar.Fields.getDeclaredFieldValue(proxy, "CGLIB$CALLBACK_0");

//            Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
//            advised.setAccessible(true);
//
//            T target = (T) ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
            AdvisedSupport advisedSupport = ClassSugar.Fields.getDeclaredFieldValue(dynamicAdvisedInterceptor, "advised");
            T target = (T) advisedSupport.getTargetSource().getTarget();
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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


