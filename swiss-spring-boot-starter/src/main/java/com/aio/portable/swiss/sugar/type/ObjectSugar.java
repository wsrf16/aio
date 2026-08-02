package com.aio.portable.swiss.sugar.type;

public abstract class ObjectSugar {
    public static <T> T getNotEmptyValue(T t, T defaultVal) {
        return t != null ? t : defaultVal;
    }

//    public static <T> T getNotEmptyValue(Object t, T ifNotNull, T ifNull) {
//        return t != null ? ifNotNull : ifNull;
//    }

    public static boolean isTrue(Boolean b) {
        return b != null && b;
    }

    public static boolean isFalse(Boolean b) {
        return b != null && !b;
    }
}
