package com.aio.portable.swiss.suite.log.solution.local;

import com.aio.portable.swiss.suite.log.solution.console.ConsoleLog;

public class LocalLog extends ConsoleLog {
    public LocalLog() {
        super();
    }

    public LocalLog(String name) {
        super(name);
    }

    public LocalLog(Class<?> clazz) {
        super(clazz);
    }

    public static final LocalLog getLog() {
        return new LocalLog();
    }

    public static final LocalLog getLog(String name) {
        return new LocalLog(name);
    }

    public static final LocalLog getLog(Class<?> clazz) {
        return new LocalLog(clazz);
    }
}
