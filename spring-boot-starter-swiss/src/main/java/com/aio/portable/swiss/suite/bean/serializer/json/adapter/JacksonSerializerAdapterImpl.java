package com.aio.portable.swiss.suite.bean.serializer.json.adapter;

import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.fasterxml.jackson.core.type.TypeReference;

public class JacksonSerializerAdapterImpl implements StringSerializerAdapter {
    @Override
    public <T> String serialize(T t) {
        return JacksonSugar.obj2Json(t);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
        return JacksonSugar.json2T(json, clazz);
    }

    public <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        return JacksonSugar.json2T(json, valueTypeRef);
    }
}
