package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.adapter.*;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.JackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.ShortJackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.yaml.adapter.YamlSerializerAdapterImpl;

public abstract class JacksonAdapterFactory {
    public static final YamlSerializerAdapterImpl buildYaml() {
        return new YamlSerializerAdapterImpl();
    }

    public static final JackXmlSerializerAdapterImpl buildXml() {
        return new JackXmlSerializerAdapterImpl();
    }

    public static final ShortJackXmlSerializerAdapterImpl buildShortJsonXml() {
        return new ShortJackXmlSerializerAdapterImpl();
    }

    public static final ByteSerializerAdapterImpl buildGson() {
        return new ByteSerializerAdapterImpl();
    }

    public static final JacksonSerializerAdapterImpl buildJson() {
        return new JacksonSerializerAdapterImpl();
    }

    public static final LongJacksonSerializerAdapterImpl buildLongJson() {
        return new LongJacksonSerializerAdapterImpl();
    }

    public static final ShortJacksonSerializerAdapterImpl buildShortJson() {
        return new ShortJacksonSerializerAdapterImpl();
    }

    public static final SilentJacksonSerializerAdapterImpl buildSilentJackson() {
        return new SilentJacksonSerializerAdapterImpl();
    }

    public static final SilentLongJacksonSerializerAdapterImpl buildSilentLongJson() {
        return new SilentLongJacksonSerializerAdapterImpl();
    }

    public static final SilentShortJacksonSerializerAdapterImpl buildSilentShortJson() {
        return new SilentShortJacksonSerializerAdapterImpl();
    }

    public static final ByteSerializerAdapterImpl buildByte() {
        return new ByteSerializerAdapterImpl();
    }

}
