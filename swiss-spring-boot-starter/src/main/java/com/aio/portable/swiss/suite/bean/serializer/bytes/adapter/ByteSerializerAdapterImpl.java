package com.aio.portable.swiss.suite.bean.serializer.bytes.adapter;

import com.aio.portable.swiss.suite.bean.serializer.ByteSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.SerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.bytes.ByteSugar;
import com.aio.portable.swiss.suite.bean.serializer.yaml.YamlSugar;

public class ByteSerializerAdapterImpl implements ByteSerializerAdapter {
    @Override
    public <T> byte[] serialize(T t) {
        return ByteSugar.toByteArray(t);
    }

    @Override
    public <T> T deserialize(byte[] serial, Class<T> clazz) {
        return (T) ByteSugar.toObject(serial);
    }
}
