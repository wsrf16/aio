package com.aio.portable.swiss.sugar.type;

import org.springframework.lang.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

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

    public static boolean isEmpty(@Nullable Object obj) {
        if (obj == null) {
            return true;
        }

        if (obj instanceof Optional) {
            return !((Optional<?>) obj).isPresent();
        }
        if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        }
        if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        // else
        return false;
    }
}
