package com.aio.portable.swiss.sugar.concurrent;

import java.time.Duration;

public class ThreadSugar extends Thread {
    public static final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static final void sleep(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static final void sleepSeconds(long s) {
        try {
            Thread.sleep(Duration.ofSeconds(s).toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
