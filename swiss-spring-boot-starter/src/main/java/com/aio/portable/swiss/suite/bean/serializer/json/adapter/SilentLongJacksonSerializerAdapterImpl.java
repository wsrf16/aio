package com.aio.portable.swiss.suite.bean.serializer.json.adapter;

import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;

public class SilentLongJacksonSerializerAdapterImpl implements StringSerializerAdapter {
    @Override
    public <T> String serialize(T t) {
        return JacksonSugar.obj2LongJson(t, false);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
        return JacksonSugar.json2T(json, clazz);
    }
}