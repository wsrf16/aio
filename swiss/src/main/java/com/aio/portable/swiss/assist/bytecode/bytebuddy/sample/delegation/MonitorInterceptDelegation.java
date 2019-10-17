package com.aio.portable.swiss.assist.bytecode.bytebuddy.sample.delegation;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;


public class MonitorInterceptDelegation {
    @RuntimeType
    public static Object dynamicIntercept(@This Object proxy,
                                   @AllArguments Object[] arguments,
                                   @Origin Class clazz,
                                   @Origin Method method,
                                   @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            long end = System.currentTimeMillis();
            long during = end - start;

            if (during >= 200) {
                System.out.println("Cost time:");
                System.out.println("    " + method + ": took " + during + "ms");
            }
        }
    }

    @RuntimeType
    public static Object staticIntercept(@AllArguments Object[] arguments,
                                    @Origin Class clazz,
                                    @Origin Method method,
                                    @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            long end = System.currentTimeMillis();
            long during = end - start;

            if (during >= 200) {
                System.out.println("Cost time:");
                System.out.println("    " + method + ": took " + during + "ms");
            }
        }
    }

    @RuntimeType
    public void intercept() throws Exception {
        long start = System.currentTimeMillis();
        if (1 == 1)
            return;
    }


}
