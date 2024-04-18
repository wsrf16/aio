package com.aio.portable.swiss.suite.bean.serializer.xml.adapter;

import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.bean.serializer.xml.XmlSugar;

public class JackXmlSerializerAdapterImpl implements StringSerializerAdapter {
    @Override
    public <T> String serialize(T t) {
        return XmlSugar.obj2Xml(t);
    }

    @Override
    public <T> T deserialize(String xml, Class<T> clazz) {
        return XmlSugar.xml2T(xml, clazz);
    }
}
