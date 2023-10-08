package com.aio.portable.swiss.sugar.resource.function;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface LambdaFunction<T, R> extends Function<T, R>, Serializable {
}
