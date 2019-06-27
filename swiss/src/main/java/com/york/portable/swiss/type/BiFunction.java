package com.york.portable.swiss.type;

import java.util.function.Function;

@FunctionalInterface
public interface BiFunction<T, U, V, R> {
    R apply(T t, U u, V v);

    default <TRANSR> BiFunction<T, U, V, TRANSR> andThen(Function<? super R, ? extends TRANSR> after) {
        return (t, u, v) -> {
//                apply(t, u, v);
            return after.apply(apply(t, u, v));
        };
    }
}
