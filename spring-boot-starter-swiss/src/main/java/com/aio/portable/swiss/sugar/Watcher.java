package com.aio.portable.swiss.sugar;

import org.springframework.util.StopWatch;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public abstract class Watcher {
    public final static StopWatch watch(Runnable runnable) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        runnable.run();
        stopWatch.stop();
        return stopWatch;
    }

    /**
     * Get CPU time in nanoseconds.
     */
    public final static long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime() : 0L;
    }


    /**
     * Get user time in nanoseconds.
     */
    public final static long getUserTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean
                .getCurrentThreadUserTime() : 0L;
    }
}
