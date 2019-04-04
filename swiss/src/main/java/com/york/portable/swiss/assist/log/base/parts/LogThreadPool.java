package com.york.portable.swiss.assist.log.base.parts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogThreadPool {
    static LogThreadPool pool = new LogThreadPool();

    private LogThreadPool() {}

    public ExecutorService executor = Executors.newCachedThreadPool();

    public static synchronized LogThreadPool newInstance() {
        return pool;
    }
}
