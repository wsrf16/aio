package com.aio.portable.swiss.suite.bean.serializer;

public interface SerializerConverter {
    <T> String serialize(T t);
    <T> T deserialize(String json, Class<T> clazz);
}




