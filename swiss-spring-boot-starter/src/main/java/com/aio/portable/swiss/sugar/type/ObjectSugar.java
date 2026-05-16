package com.aio.portable.swiss.sugar.type;

public abstract class ObjectSugar {
    public static <T> T emptyIfNull(T t, T defaultVal) {
        return t != null ? t : defaultVal;
    }

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean isFalse(Boolean b) {
        return b != null && !b;
    }
}
