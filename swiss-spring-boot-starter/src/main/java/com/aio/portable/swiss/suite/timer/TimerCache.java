package com.aio.portable.swiss.suite.timer;

import com.aio.portable.swiss.sugar.concurrent.ThreadSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.schedule.task.TaskScheduleSugar;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TimerCache<T> {
    private static final LocalLog log = LocalLog.getLog(LogHubProperties.class);

    private ThreadPoolTaskScheduler scheduler;


    private T data;
    private final Callable<T> refresh;
//    private Duration interval = Duration.ofMinutes(5);
//    private boolean once = false;
//    private Thread updateThread = new Thread() {
//        @Override
//        public void run() {
//            while (loop) {
//                try {
//                    update();
//                    setOnce(true);
//                    Thread.sleep(interval.toMillis());
//                } catch (InterruptedException e) {
//                    log.w(e);
//                }
//            }
//        }
//    };

    private boolean loop = false;

    private TimerCache(ThreadPoolTaskScheduler scheduler, Callable<T> refresh) {
        this.scheduler = scheduler;
        this.refresh = refresh;
    }

    public static final <T> TimerCache<T> build(ThreadPoolTaskScheduler scheduler, Callable<T> refresh) {
        TimerCache<T> instance = new TimerCache<>(scheduler, refresh);
//        instance.setInterval(expire);
        return instance;
    }

//    public static final <T> TimerCache<T> build(Duration expire, Supplier<T> refresh) {
//        TimerCache<T> instance = new TimerCache<>(refresh);
//        instance.setInterval(expire);
//        return instance;
//    }

    private synchronized void update() {
        try {
            T t = refresh.call();
            this.setData(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


//    public static final <T> Runnable toRunnable(Callable<T> refresh, Consumer<T> storage) {
//        return () -> {
//            try {
//                T t = refresh.call();
//                storage.accept(t);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        };
//    }

    // ？？？？？
    public synchronized void start(String cron, boolean runAtOnce) {
        this.setLoop(true);
        if (!isAlive()) {
//            updateThread.start();
            TaskScheduleSugar.schedule(this.scheduler, this::update, cron, runAtOnce);
        }
    }

    public synchronized void start(Duration duration, boolean runAtOnce) {
        this.setLoop(true);
        if (!isAlive()) {
//            updateThread.start();
            TaskScheduleSugar.scheduleAtFixedRate(this.scheduler, this::update, duration, runAtOnce);
        }
    }

    public void stop() {
        this.setLoop(false);
    }

    public boolean isAlive() {
        return scheduler.getActiveCount() > 0;
//        return updateThread.isAlive();
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

//    public Duration getInterval() {
//        return interval;
//    }
//
//    public void setInterval(Duration interval) {
//        this.interval = interval;
//    }

//    public boolean hasOnce() {
//        return once;
//    }
//
//    private void setOnce(boolean once) {
//        this.once = once;
//    }
}
