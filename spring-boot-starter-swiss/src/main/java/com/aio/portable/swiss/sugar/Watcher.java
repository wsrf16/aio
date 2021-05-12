package com.aio.portable.swiss.sugar;

import java.util.function.Supplier;

public abstract class Watcher {
    public static long watch(Supplier<Void> supplier) {
        long prevTime = System.currentTimeMillis();
        supplier.get();
        long nextTime = System.currentTimeMillis();
        long during = nextTime - prevTime;
        return during;
    }
}
