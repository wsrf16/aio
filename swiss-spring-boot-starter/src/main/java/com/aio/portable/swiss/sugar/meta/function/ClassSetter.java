package com.aio.portable.swiss.sugar.meta.function;

import java.io.Serializable;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ClassSetter<T, R> extends BiConsumer<T, R>, Serializable {
}
