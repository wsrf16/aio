package com.aio.portable.park;

import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggerRepository;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.*;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Logger {
    private static org.slf4j.Logger logger;
    private static final String FQCN = Logger.class.getName();

    static {
        try {
            Enhancer eh = new Enhancer();
            eh.setSuperclass(org.apache.log4j.Logger.class);
            eh.setCallbackType(LogInterceptor.class);
            Class c = eh.createClass();
            Enhancer.registerCallbacks(c, (Callback[]) new LogInterceptor[]{new LogInterceptor()});//？？？？？？？？？？？

            Constructor<org.apache.log4j.Logger> constructor = c.getConstructor(String.class);
            org.apache.log4j.Logger loggerProxy = constructor.newInstance(Logger.class.getName());

            LoggerRepository loggerRepository = LogManager.getLoggerRepository();
            org.apache.log4j.spi.LoggerFactory lf = ReflectionUtil.getFieldValue(loggerRepository, "defaultFactory");
            Object loggerFactoryProxy = Proxy.newProxyInstance(
                    LoggerFactory.class.getClassLoader(),
                    new Class[]{LoggerFactory.class},
                    (InvocationHandler) new NewLoggerHandler(loggerProxy)//？？？？？？？？？？？
            );

            ReflectionUtil.setFieldValue(loggerRepository, "defaultFactory", loggerFactoryProxy);
            logger = org.slf4j.LoggerFactory.getLogger(Logger.class.getName());
            ReflectionUtil.setFieldValue(loggerRepository, "defaultFactory", lf);
        } catch (
                IllegalAccessException |
                        NoSuchMethodException |
                        InvocationTargetException |
                        InstantiationException e) {
            throw new RuntimeException("初始化Logger失败", e);
        }
    }

    private static class LogInterceptor implements MethodInterceptor {
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            // 只拦截log方法。
            if (objects.length != 4 || !method.getName().equals("log"))
                return methodProxy.invokeSuper(o, objects);
            objects[0] = FQCN;
            return methodProxy.invokeSuper(o, objects);
        }
    }

    private static class NewLoggerHandler implements InvocationHandler {
        private final org.apache.log4j.Logger proxyLogger;

        public NewLoggerHandler(org.apache.log4j.Logger proxyLogger) {
            this.proxyLogger = proxyLogger;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return proxyLogger;
        }
    }

    // 剩下的Logger需要封装的方法可以根据自己的需要来实现
    // 我个人认为slf4j的api足够好用了，所以大部分只是写了一些类似下面的代码
    public static void debug(String msg) {
        logger.debug(msg);
    }
}



class ReflectionUtil {
    public static <T> T getFieldValue(@NotNull Object object,
                                      @NotNull String fullName) throws IllegalAccessException {
        return getFieldValue(object, fullName, false);
    }

    public static <T> T getFieldValue(@NotNull Object object,
                                      @NotNull String fieldName,
                                      boolean traceable) throws IllegalAccessException {
        Field field;
        String[] fieldNames = fieldName.split("\\.");
        for (String targetField : fieldNames) {
            field = searchField(object.getClass(), targetField, traceable);
            if (field == null)
                return null;

            object = getValue(object, field);
        }

        return (T) object;
    }

    private static Field searchField(Class c, String targetField, boolean traceable) {
        do {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                if (f.getName().equals(targetField)) {
                    return f;
                }
            }
            c = c.getSuperclass();
            traceable = traceable && c != Object.class;
        } while (traceable);

        return null;
    }

    private static <T> T getValue(Object target, Field field) throws IllegalAccessException {
        if (!field.isAccessible())
            field.setAccessible(true);
        return (T) field.get(target);
    }

    public static boolean setFieldValue(@NotNull Object target,
                                        @NotNull String fieldName,
                                        @NotNull Object value) throws IllegalAccessException {
        return setFieldValue(target, fieldName, value, false);
    }

    public static boolean setFieldValue(@NotNull Object target,
                                        @NotNull String fieldName,
                                        @NotNull Object value,
                                        boolean traceable) throws IllegalAccessException {
        Field field = searchField(target.getClass(), fieldName, traceable);
        if (field != null)
            return setValue(field, target, value);
        return false;
    }

    private static boolean setValue(Field field, Object target, Object value) throws IllegalAccessException {
        if (!field.isAccessible())
            field.setAccessible(true);
        field.set(target, value);
        return true;
    }
}