package com.aio.portable.swiss.suite.bean.serializer;

public interface SerializerAdapter<S> {
    <T> S serialize(T t);
    <T> T deserialize(S serial, Class<T> clazz);
}




