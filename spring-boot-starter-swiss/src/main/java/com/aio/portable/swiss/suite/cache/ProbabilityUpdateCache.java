package com.aio.portable.swiss.suite.cache;

import java.util.function.Supplier;

public class ProbabilityUpdateCache<T> {
    private T t;
    private Supplier<T> refresh;

    public ProbabilityUpdateCache(Supplier<T> refresh) {
        this.refresh = refresh;
    }

    public void update() {
        T t = refresh.get();
        this.t = t;
    }

    public T get() {
        return this.t;
    }
}
