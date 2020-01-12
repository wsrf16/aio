package com.aio.portable.swiss.sugar;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public abstract class ThreadPoolTaskExecutors {
    public final static ThreadPoolTaskExecutor newInstance(int corePoolSize,
                                                           int maxPoolSize,
                                                           boolean waitForJobsToCompleteOnShutdown) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(corePoolSize);
        pool.setMaxPoolSize(maxPoolSize);
        pool.setWaitForTasksToCompleteOnShutdown(waitForJobsToCompleteOnShutdown);
        return pool;
    }

    public final static ThreadPoolTaskExecutor newInstanceAndInitialize(int corePoolSize,
                                                           int maxPoolSize,
                                                           boolean waitForJobsToCompleteOnShutdown) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = newInstance(corePoolSize, maxPoolSize, waitForJobsToCompleteOnShutdown);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
