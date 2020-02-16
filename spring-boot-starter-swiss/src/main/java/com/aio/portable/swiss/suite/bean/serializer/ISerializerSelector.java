package com.aio.portable.swiss.suite.bean.serializer;

@FunctionalInterface
public interface ISerializerSelector {
    <T> String serialize(T t);
}