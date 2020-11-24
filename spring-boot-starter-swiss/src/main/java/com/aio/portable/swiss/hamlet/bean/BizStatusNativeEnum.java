package com.aio.portable.swiss.hamlet.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class BizStatusNativeEnum {
    protected static BizStatusNativeEnum instance;

    public static BizStatusNativeEnum singletonInstance() {
        return instance;
    }

    protected BizStatusNativeEnum() {
        synchronized (BizStatusNativeEnum.class) {
            BizStatusNativeEnum.instance = BizStatusNativeEnum.instance == null ? this : BizStatusNativeEnum.instance;
        }
    }

    public BizStatus succeed() {
        return new BizStatus(0, "请求成功");
    }

    public BizStatus failed() {
        return new BizStatus(1, "请求失败");
    }

    public BizStatus exception() {
        return new BizStatus(2, "请求异常");
    }

    public BizStatus invalid() {
        return new BizStatus(3, "请求无效");
    }

    public BizStatus unauthorized() {
        return new BizStatus(4, "无效授权");
    }

    public static BizStatus staticSucceed() {
        return instance.succeed();
    }

    public static BizStatus staticFailed() {
        return instance.failed();
    }

    public static BizStatus staticException() {
        return instance.exception();
    }

    public static BizStatus staticInvalid() {
        return instance.invalid();
    }

    public static BizStatus staticUnauthorized() {
        return instance.unauthorized();
    }




    public static List<BizStatus> values() {
        Class<BizStatusNativeEnum> clazz = BizStatusNativeEnum.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Method> methodList = Arrays.stream(declaredMethods).filter(c -> !Modifier.isStatic(c.getModifiers()) && c.getReturnType() == BizStatus.class).collect(Collectors.toList());

        Stream<BizStatus> bizStatusStream = methodList.stream().map(c -> {
            try {
                BizStatus bizStatus = (BizStatus)c.invoke(instance);
                return bizStatus;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });
        List<BizStatus> bizStatusList = bizStatusStream.collect(Collectors.toList());
        return bizStatusList;
    }
}
