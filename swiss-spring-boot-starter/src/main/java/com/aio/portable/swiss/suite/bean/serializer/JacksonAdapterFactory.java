package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.adapter.ByteSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.JacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.LongJacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.ShortJacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.SilentJacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.SilentLongJacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.SilentShortJacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.JackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.ShortJackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.yaml.adapter.YamlSerializerAdapterImpl;

public abstract class JacksonAdapterFactory {
    public static YamlSerializerAdapterImpl buildYaml() {
        return new YamlSerializerAdapterImpl();
    }

    public static JackXmlSerializerAdapterImpl buildXml() {
        return new JackXmlSerializerAdapterImpl();
    }

    public static ShortJackXmlSerializerAdapterImpl buildShortJsonXml() {
        return new ShortJackXmlSerializerAdapterImpl();
    }

    public static ByteSerializerAdapterImpl buildGson() {
        return new ByteSerializerAdapterImpl();
    }

    public static JacksonSerializerAdapterImpl buildJson() {
        return new JacksonSerializerAdapterImpl();
    }

    public static LongJacksonSerializerAdapterImpl buildLongJson() {
        return new LongJacksonSerializerAdapterImpl();
    }

    public static ShortJacksonSerializerAdapterImpl buildShortJson() {
        return new ShortJacksonSerializerAdapterImpl();
    }

    public static SilentJacksonSerializerAdapterImpl buildSilentJackson() {
        return new SilentJacksonSerializerAdapterImpl();
    }

    public static SilentLongJacksonSerializerAdapterImpl buildSilentLongJson() {
        return new SilentLongJacksonSerializerAdapterImpl();
    }

    public static SilentShortJacksonSerializerAdapterImpl buildSilentShortJson() {
        return new SilentShortJacksonSerializerAdapterImpl();
    }

    public static ByteSerializerAdapterImpl buildByte() {
        return new ByteSerializerAdapterImpl();
    }

}
