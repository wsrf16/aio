package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.bean.serializer.SerializerAdapterFactory;
import com.aio.portable.swiss.suite.bean.serializer.StringSerializerAdapter;
import com.aio.portable.swiss.suite.log.support.LevelEnum;

@FunctionalInterface
public interface Printer {
    <T> void println(T record, LevelEnum level);

    default void dispose(){}

    default StringSerializerAdapter getSmartSerializerAdapter(LevelEnum level) {
        StringSerializerAdapter serializer = level.getPriority() < LevelEnum.WARN.getPriority() ?
                SerializerAdapterFactory.buildSilentJackson() : SerializerAdapterFactory.buildSilentLongJackson();
        return serializer;
    }

    default StringSerializerAdapter getSerializerAdapter() {
        return SerializerAdapterFactory.buildSilentJackson();
    }

    default StringSerializerAdapter getLooseSerializerAdapter() {
        return SerializerAdapterFactory.buildSilentLongJackson();
    }


}
