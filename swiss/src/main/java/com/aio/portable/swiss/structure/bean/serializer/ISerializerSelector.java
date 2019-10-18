package com.aio.portable.swiss.structure.bean.serializer;

@FunctionalInterface
public interface ISerializerSelector {
    <T> String serialize(T t);
}