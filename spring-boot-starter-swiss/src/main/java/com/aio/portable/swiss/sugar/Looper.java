package com.aio.portable.swiss.sugar;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Looper {
    public boolean running = false;

    public boolean prevent = false;

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    Runnable runnable;
    long atLeastInterval;
    TimeUnit timeUnit;

    public void tryToStop() {
        prevent = true;
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Looper(Runnable runnable, long atLeastInterval, TimeUnit timeUnit) {
        this.runnable = runnable;
        this.atLeastInterval = atLeastInterval;
        this.timeUnit = timeUnit;
    }


    public synchronized void start() {
        long prevTime, nextTime;

        running = true;
        while (!prevent) {
            prevTime = System.currentTimeMillis();
            runnable.run();
            nextTime = System.currentTimeMillis();
            if (nextTime - prevTime < timeUnit.toMillis(atLeastInterval)) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        countDownLatch.countDown();
        countDownLatch = new CountDownLatch(1);

        prevent = false;
        running = false;
    }

    public boolean isRunning() {
        return running;
    }
}