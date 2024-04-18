package com.aio.portable.swiss.suite.bean.serializer.json.adapter;

import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.json.GsonSugar;

public class ByteSerializerAdapterImpl implements StringSerializerAdapter {
    @Override
    public <T> String serialize(T t) {
        return GsonSugar.obj2Json(t);
    }

    @Override
    public <T> T deserialize(String json, Class<T> clazz) {
        return GsonSugar.json2T(json, clazz);
    }
}
