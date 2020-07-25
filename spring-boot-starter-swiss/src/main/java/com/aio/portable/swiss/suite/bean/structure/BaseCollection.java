package com.aio.portable.swiss.suite.bean.structure;

import java.util.Map;

public interface BaseCollection<T> {
    void add(T t);

    void remove(String name);

//    void remove(T listener);

    void clear();

    T get(String name);

    boolean exists(String name);

    Map<String, T> collection();
    
}
