package com.aio.portable.swiss.design.singleton;

import com.aio.portable.swiss.sugar.meta.ClassSugar;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseSingletonInstance {
    private static final BaseSingletonInstance SELF = new BaseSingletonInstance();

    protected Map<Class<?>, Object> instanceMap = new ConcurrentHashMap<>();

//    public synchronized static SingletonInstance self() {
//        return SINGLETON_INSTANCE == null ? new SingletonInstance() : SINGLETON_INSTANCE;
//    }

    public synchronized static <T> T getSingleton(Class<T> clazz) {
        T t;
        if (SELF.instanceMap.containsKey(clazz)) {
            t = (T) SELF.instanceMap.get(clazz);
        } else {
            t = ClassSugar.newDeclaredInstance(clazz);
            SELF.instanceMap.put(clazz, t);
        }
        return t;
    }

    public synchronized static void importSingletonInstance(Object instance) {
        SELF.instanceMap.put(instance.getClass(), instance);
    }
}