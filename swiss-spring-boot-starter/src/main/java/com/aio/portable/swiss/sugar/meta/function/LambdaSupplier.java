package com.aio.portable.swiss.sugar.meta.function;

import java.io.Serializable;
import java.util.function.Supplier;

@FunctionalInterface
public interface LambdaSupplier<T> extends Supplier<T>, Serializable {
}
