package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.suite.bean.type.BiFunction;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

public class JDKProxy<T> implements InvocationHandler {

    private T t;
    private BiFunction<Object, Method, Object[], Object> aopFunction;

    public JDKProxy(T t) {
        this.t = t;
    }

    public T createProxy() {
        T proxy = (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                this);
        return proxy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//        if ("update".equals(method.getName())) {
//            System.out.println("权限校验");
//            return method.invoke(t, args);
//        }
//        return method.invoke(t, args);
        if (aopFunction == null)
            throw new NullPointerException("JDKProxy::getAopFunction");
        Object apply = aopFunction.apply(proxy, method, args);
        return method.invoke(t, args);
    }

//    public Object set(BiFunction<Object, Method, Object[], Object> aopFunction, Object proxy, Method method, Object[] args) {
//        BiFunction<Object, Method, Object[], Object> biFunction = aopFunction;
//        return biFunction.apply(proxy, method, args);
//    }





    public BiFunction<Object, Method, Object[], Object> getAopFunction() {
        return aopFunction;
    }

    public void setAopFunction(BiFunction<Object, Method, Object[], Object> aopFunction) {
        this.aopFunction = aopFunction;
    }
}


