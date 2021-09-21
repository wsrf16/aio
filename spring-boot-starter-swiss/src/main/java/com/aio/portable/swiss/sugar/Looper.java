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

    public Looper(Runnable runnable, long atLeastInterval, TimeUnit timeUnit) {
        this.runnable = runnable;
        this.atLeastInterval = atLeastInterval;
        this.timeUnit = timeUnit;
    }

    public synchronized void start() {
        running = true;
        try {
            long time = 0;
            while (!prevent) {
                if (System.currentTimeMillis() - time < timeUnit.toMillis(atLeastInterval)) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    time = System.currentTimeMillis();
                    Watcher.watch(runnable).getTotalTimeMillis();
                }
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            countDownLatch.countDown();
            countDownLatch = new CountDownLatch(1);

            prevent = false;
            running = false;
        }
    }

    public void tryToStop(long timeout, TimeUnit timeUnit) {
        prevent = true;
        try {
            countDownLatch.await(timeout, timeUnit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        prevent = true;
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isRunning() {
        return running;
    }
}