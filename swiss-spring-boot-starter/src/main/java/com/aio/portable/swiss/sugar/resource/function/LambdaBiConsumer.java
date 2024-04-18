package com.aio.portable.swiss.sugar.resource.function;

import java.io.Serializable;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface LambdaBiConsumer<T, R> extends BiConsumer<T, R>, Serializable {
}
