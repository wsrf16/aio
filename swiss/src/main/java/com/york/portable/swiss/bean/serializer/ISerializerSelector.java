package com.york.portable.swiss.bean.serializer;

@FunctionalInterface
public interface ISerializerSelector {
    <T> String serialize(T t);
}