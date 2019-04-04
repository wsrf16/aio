package com.york.portable.swiss.bean;

/**
 * Created by York on 2017/11/23.
 */
public class HungrySingleton<T> {
    private final T instance;

    private HungrySingleton(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        instance = clazz.newInstance();
    }



    public T instance(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (instance == null)
            synchronized (this) {
                if (instance == null)
                    return clazz.newInstance();
            }
        return (T) instance;
    }

}
