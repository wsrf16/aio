package com.aio.portable.swiss.sugar.meta.function;

import java.io.Serializable;
import java.util.function.Consumer;

@FunctionalInterface
public interface LambdaConsumer<T> extends Consumer<T>, Serializable {
}
