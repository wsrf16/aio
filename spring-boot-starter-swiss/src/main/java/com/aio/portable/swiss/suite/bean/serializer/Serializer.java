package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;

public interface Serializer {
    <T> String serialize(T t);
    <T> T deserialize(String json, Class<T> clazz);
}




