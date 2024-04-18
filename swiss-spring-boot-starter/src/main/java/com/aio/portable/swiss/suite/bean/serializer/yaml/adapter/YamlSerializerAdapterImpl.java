package com.aio.portable.swiss.suite.bean.serializer.yaml.adapter;

import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.yaml.YamlSugar;

public class YamlSerializerAdapterImpl implements StringSerializerAdapter {
    @Override
    public <T> String serialize(T t) {
        return YamlSugar.obj2Yaml(t);
    }

    @Override
    public <T> T deserialize(String yaml, Class<T> clazz) {
        return YamlSugar.yaml2T(yaml, clazz);
    }
}
