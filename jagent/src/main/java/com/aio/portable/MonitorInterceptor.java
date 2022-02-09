package com.aio.portable;

import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.concurrent.Callable;


public class MonitorInterceptor {
    private static final String prefix(Integer threshold) {
        String prefix = MessageFormat.format("Cost time(>={0}ms):", threshold);
        return prefix;
    }

    @RuntimeType
    public static final Object intercept(@This Object proxy,
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

            if (during >= Config.THRESHOLD) {
                System.out.println(prefix(Config.THRESHOLD));
                System.out.println("    " + method + ": took " + during + "ms");
            }
        }
    }

    @RuntimeType
    public static final Object staticIntercept(@AllArguments Object[] arguments,
                                         @Origin Class clazz,
                                         @Origin Method method,
                                         @SuperCall Callable<?> callable) throws Exception {
        long start = System.currentTimeMillis();
        try {
            return callable.call();
        } finally {
            long end = System.currentTimeMillis();
            long during = end - start;

            if (during >= Config.THRESHOLD) {
                System.out.println(prefix(Config.THRESHOLD));
                System.out.println("    " + method + ": took " + during + "ms");
            }
        }
    }

    @RuntimeType
    public Object intercept1() throws Exception {
        long start = System.currentTimeMillis();
        try {
            if (1 == 1)
                return null;
        } finally {
//                System.out.println(method + ": took " + (System.currentTimeMillis() - start) + "ms");
            return null;
        }
    }


}
