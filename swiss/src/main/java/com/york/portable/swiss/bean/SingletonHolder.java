package com.york.portable.swiss.bean;

/**
 * Created by York on 2017/11/23.
 */
public class SingletonHolder<T> {
    private Class<T> clazz;
    private T instance;

    public SingletonHolder(Class<T> clazz) {
        this.clazz = clazz;
        this.instance = instance();
    }


    public T instance() {
        try {
            if (instance == null)
                synchronized (this) {
                    if (instance == null) {
                        return clazz.newInstance();
                    }
                }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
