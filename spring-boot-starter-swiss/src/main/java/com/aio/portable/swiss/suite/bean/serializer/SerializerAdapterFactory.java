package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.adapter.*;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.JackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.ShortJackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.yaml.adapter.YamlSerializerAdapterImpl;

public abstract class SerializerAdapterFactory {
    public static final YamlSerializerAdapterImpl buildJacksonYaml() {
        return new YamlSerializerAdapterImpl();
    }

    public static final JackXmlSerializerAdapterImpl buildJacksonXml() {
        return new JackXmlSerializerAdapterImpl();
    }

    public static final ShortJackXmlSerializerAdapterImpl buildShortJacksonXml() {
        return new ShortJackXmlSerializerAdapterImpl();
    }

    public static final ByteSerializerAdapterImpl buildGson() {
        return new ByteSerializerAdapterImpl();
    }

    public static final JacksonSerializerAdapterImpl buildJackson() {
        return new JacksonSerializerAdapterImpl();
    }

    public static final LongJacksonSerializerAdapterImpl buildLongJackson() {
        return new LongJacksonSerializerAdapterImpl();
    }

    public static final ShortJacksonSerializerAdapterImpl buildShortJackson() {
        return new ShortJacksonSerializerAdapterImpl();
    }

    public static final SilentJacksonSerializerAdapterImpl buildSilentJackson() {
        return new SilentJacksonSerializerAdapterImpl();
    }

    public static final SilentLongJacksonSerializerAdapterImpl buildSilentLongJackson() {
        return new SilentLongJacksonSerializerAdapterImpl();
    }

    public static final SilentShortJacksonSerializerAdapterImpl buildSilentShortJackson() {
        return new SilentShortJacksonSerializerAdapterImpl();
    }

    public static final ByteSerializerAdapterImpl buildByte() {
        return new ByteSerializerAdapterImpl();
    }

}
