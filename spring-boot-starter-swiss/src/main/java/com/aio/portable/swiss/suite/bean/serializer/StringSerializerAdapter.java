package com.aio.portable.swiss.suite.bean.serializer;

public interface StringSerializerAdapter extends SerializerAdapter<String> {
    <T> String serialize(T t);
    <T> T deserialize(String text, Class<T> clazz);
}




