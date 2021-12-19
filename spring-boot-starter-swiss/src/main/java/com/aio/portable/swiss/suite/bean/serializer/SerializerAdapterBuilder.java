package com.aio.portable.swiss.suite.bean.serializer;

import com.aio.portable.swiss.suite.bean.serializer.json.adapter.*;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.JackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.xml.adapter.ShortJackXmlSerializerAdapterImpl;
import com.aio.portable.swiss.suite.bean.serializer.yaml.adapter.YamlSerializerAdapterImpl;

public abstract class SerializerAdapterBuilder {
    public final static StringSerializerAdapter buildYaml() {
        return new YamlSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildJackXml() {
        return new JackXmlSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildShortJackXml() {
        return new ShortJackXmlSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildGson() {
        return new ByteSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildJackson() {
        return new JacksonSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildLongJackson() {
        return new LongJacksonSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildShortJackson() {
        return new ShortJacksonSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildSilentJackson() {
        return new SilentJacksonSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildSilentLongJackson() {
        return new SilentLongJacksonSerializerAdapterImpl();
    }

    public final static StringSerializerAdapter buildSilentShortJackson() {
        return new SilentShortJacksonSerializerAdapterImpl();
    }

    public final static ByteSerializerAdapter buildByte() {
        return new com.aio.portable.swiss.suite.bean.serializer.bytes.adapter.ByteSerializerAdapterImpl();
    }

}
