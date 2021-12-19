package com.aio.portable.swiss.suite.bean.serializer;

public interface ByteSerializerAdapter extends SerializerAdapter<byte[]> {
    <T> byte[] serialize(T t);
    <T> T deserialize(byte[] serial, Class<T> clazz);
}




