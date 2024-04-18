package com.aio.portable.swiss.suite.bean.structure;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public interface PersistentBaseCollection<T> {
    void add(T t);

    void remove(String name);

//    void remove(T t);

    void clear();

    T get(String name, TypeReference<T> valueTypeRef);

    boolean exists(String name);

    Map<String, T> collection(TypeReference<T> valueTypeRef);
    
}
