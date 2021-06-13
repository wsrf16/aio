package com.aio.portable.swiss.sugar;

import java.util.function.Supplier;

public abstract class Looper {
    public boolean running = true;

    public void runLoop(Supplier<Void> supplier, int intervalMillisecondAtLeast) {
        long prevTime;
        long nextTime;

        while (!running) {
            prevTime = System.currentTimeMillis();
            supplier.get();
            nextTime = System.currentTimeMillis();
            if (nextTime - prevTime < intervalMillisecondAtLeast) {
                try {
                    // prevent running on empty
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void runLoop(Supplier<Void> supplier) {
        runLoop(supplier, 2);
    }
}