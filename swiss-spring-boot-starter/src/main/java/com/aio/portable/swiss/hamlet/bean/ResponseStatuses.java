package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.hamlet.exception.BizException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ResponseStatuses {
    protected static ResponseStatuses instance;

    public static ResponseStatuses getSingleton() {
        return instance == null ? new ClassicResponseStatuses() : instance;
    }

    public static ResponseStatuses setSingleton(ResponseStatuses instance) {
        ResponseStatuses.instance = instance;
        return instance;
    }

    protected ResponseStatuses() {
        synchronized (ResponseStatuses.class) {
            if (instance == null) {
                setSingleton(this);
            }
//            ResponseStatuses.instance = ResponseStatuses.instance != null ? ResponseStatuses.instance : this;
        }
    }

    public ResponseStatus succeed() {
        return ResponseStatuses.getSingleton().succeed();
    }

    public ResponseStatus failed() {
        return ResponseStatuses.getSingleton().failed();
    }

    public ResponseStatus exception() {
        return ResponseStatuses.getSingleton().exception();
    }

    public ResponseStatus invalid() {
        return ResponseStatuses.getSingleton().invalid();
    }

    public ResponseStatus unauthorized() {
        return ResponseStatuses.getSingleton().unauthorized();
    }

    public static ResponseStatus staticSucceed() {
        return ResponseStatuses.getSingleton().succeed();
    }

    public static ResponseStatus staticFailed() {
        return ResponseStatuses.getSingleton().failed();
    }

    public static ResponseStatus staticException() {
        return ResponseStatuses.getSingleton().exception();
    }

    public static ResponseStatus staticInvalid() {
        return ResponseStatuses.getSingleton().invalid();
    }

    public static ResponseStatus staticUnauthorized() {
        return ResponseStatuses.getSingleton().unauthorized();
    }

    public static BizException failedBizException() {
        return new BizException(staticUnauthorized().getCode(), staticUnauthorized().getMessage());
    }

    public static BizException exceptionBizException() {
        return new BizException(staticException().getCode(), staticException().getMessage());
    }

    public static BizException unauthorizedBizException() {
        return new BizException(staticUnauthorized().getCode(), staticUnauthorized().getMessage());
    }



    public static List<ResponseStatus> values() {
        Class<ResponseStatuses> clazz = ResponseStatuses.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        Stream<Method> methodStream = Arrays.stream(declaredMethods).filter(c -> !Modifier.isStatic(c.getModifiers()) && c.getReturnType() == ResponseStatus.class);

        List<ResponseStatus> responseStatusList = methodStream.map(c -> {
            try {
                ResponseStatuses singleton = getSingleton();
                ResponseStatus responseStatus = (ResponseStatus)c.invoke(singleton);
                return responseStatus;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return responseStatusList;
    }
}
