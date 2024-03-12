package com.aio.portable.swiss.suite.timer;

import com.aio.portable.swiss.sugar.concurrent.ThreadSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;

import java.time.Duration;
import java.util.function.Supplier;

public class TimerCache<T> {
    private static final LocalLog log = LocalLog.getLog(LogHubProperties.class);

    private T data;
    private final Supplier<T> refresh;
    private Duration interval = Duration.ofMinutes(5);
    private boolean once = false;
    private Thread updateThread = new Thread() {
        @Override
        public void run() {
            while (loop) {
                try {
                    update();
                    setOnce(true);
                    Thread.sleep(interval.toMillis());
                } catch (InterruptedException e) {
                    log.w(e);
                }
            }
        }
    };

    private boolean loop = false;

    private TimerCache(Supplier<T> refresh) {
        this.refresh = refresh;
    }

    public static final <T> TimerCache<T> build(Duration expire, Supplier<T> refresh) {
        TimerCache<T> instance = new TimerCache<>(refresh);
        instance.setInterval(expire);
        return instance;
    }

    private synchronized void update() {
        T t = refresh.get();
        this.setData(t);
    }

    public synchronized void start() {
        this.setLoop(true);
        if (!isAlive()) {
            updateThread.start();
        }
    }

    public void stop() {
        this.setLoop(false);
    }

    public boolean isAlive() {
        return updateThread.isAlive();
    }

    public boolean isLoop() {
        return loop;
    }

    private void setLoop(boolean loop) {
        this.loop = loop;
    }

    public T getData() {
        return data;
    }

    public T tryGetData(Duration timeout) {
        Long startTime = System.currentTimeMillis();
        while (true) {
            if (getData() == null && System.currentTimeMillis() - startTime < timeout.toMillis()) {
                ThreadSugar.sleep(100);
            } else
                break;
        }

        return getData();
    }

    public void setData(T data) {
        this.data = data;
    }

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    public boolean hasOnce() {
        return once;
    }

    private void setOnce(boolean once) {
        this.once = once;
    }
}
