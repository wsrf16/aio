package com.aio.portable.swiss.bean.serializer;

@FunctionalInterface
public interface ISerializerSelector {
    <T> String serialize(T t);
}