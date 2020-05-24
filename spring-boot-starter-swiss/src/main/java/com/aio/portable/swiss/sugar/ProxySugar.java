package com.aio.portable.swiss.sugar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface ProxySugar {
    static <T> T createProxy(Class<T> clazz, InvocationHandler handler) {
        ClassLoader classLoader = clazz.getClassLoader();
        Class<?>[] selfInterfaces = clazz.getInterfaces();
        Class<?>[] superInterfaces = clazz.getSuperclass().getInterfaces();
        Class<?>[] interfaces = CollectionSugar.concat(
                Arrays.stream(selfInterfaces).collect(Collectors.toList()),
                Arrays.stream(superInterfaces).collect(Collectors.toList())
        ).toArray(new Class<?>[selfInterfaces.length + superInterfaces.length]);
        T proxy = (T) Proxy.newProxyInstance(
                classLoader,
                interfaces,
                handler);
        return proxy;
    }

/*
    {
        ErPrinterImpl erPrinter = new ErPrinterImpl();
        ErPrinter proxy = ProxySugar.createProxy(
                erPrinter.getClass(),
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
