package com.aio.portable.swiss.suite.log.support.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class LogHubThreadExecutor {
    public static final int QUEUE_CAPACITY = 1024 * 128;
    public static final int CORE_POOL_SIZE = 10;
    public static final int MAX_POOL_SIZE = 20;
    public static final long KEEP_ALIVE_TIME = 1000 * 10;


    //    public static final ExecutorService executor = Executors.newFixedThreadPool(2, new LogSingleThreadFactory());
    private static final ExecutorService executor = new ThreadPoolExecutor(
            LogHubThreadExecutor.CORE_POOL_SIZE,
            LogHubThreadExecutor.MAX_POOL_SIZE,
            LogHubThreadExecutor.KEEP_ALIVE_TIME,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(LogHubThreadExecutor.QUEUE_CAPACITY),
            new LogHubThreadFactory(),
//        new ThreadPoolExecutor.AbortPolicy()
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    public static ExecutorService getExecutor() {
        return executor;
    }
}
