package com.aio.portable.swiss.sugar.type;

public abstract class ObjectSugar {
    public static final <T> T emptyIfNull(T t, T defaultVal) {
        return t != null ? t : defaultVal;
    }
}
