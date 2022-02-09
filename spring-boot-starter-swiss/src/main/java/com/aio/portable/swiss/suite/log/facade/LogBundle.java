package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.log.action.LogAction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LogBundle implements LogAction {
    private static final Log log = LogFactory.getLog(LogBundle.class);

    protected List<LogSingle> logList;

    public List<LogSingle> getLogList() {
        return logList;
    }

    protected LogBundle() {
        logList = new ArrayList<>();
    }

    protected LogBundle(List<LogSingle> logList) {
        this.logList = logList;
    }


    public void dispose() {
        logList.forEach(it -> {
            it.dispose();
            it = null;
        });
    }

    public static void silence(Runnable runnable) {
        ThrowableSugar.catchThenHandle(runnable, e -> log.warn(e));
    }

    public void verbose(String message) {
        logList.forEach(it -> silence(() -> it.verbose(message)));
    }

    public <T> void verbose(T t) {
        logList.forEach(it -> silence(() -> it.verbose(t)));
    }

    public void verbose(String summary, String message) {
        logList.forEach(it -> silence(() -> it.verbose(summary, message)));
    }

    public <T> void verbose(String summary, T t) {
        logList.forEach(it -> silence(() -> it.verbose(summary, t)));
    }

    public <T> void verbose(String summary, String message, T t) {
        logList.forEach(it -> silence(() -> it.verbose(summary, message, t)));
    }

    public void trace(String message) {
        logList.forEach(it -> silence(() -> it.trace(message)));
    }

    public <T> void trace(T t) {
        logList.forEach(it -> silence(() -> it.trace(t)));
    }

    public void trace(String summary, String message) {
        logList.forEach(it -> silence(() -> it.trace(summary, message)));
    }

    public <T> void trace(String summary, T t) {
        logList.forEach(it -> silence(() -> it.trace(summary, t)));
    }

    public <T> void trace(String summary, String message, T t) {
        logList.forEach(it -> silence(() -> it.trace(summary, message, t)));
    }

    public void info(String message) {
        logList.forEach(it -> silence(() -> it.info(message)));
    }

    public <T> void info(T t) {
        logList.forEach(it -> silence(() -> it.info(t)));
    }

    public void info(String summary, String message) {
        logList.forEach(it -> silence(() -> it.info(summary, message)));
    }

    public <T> void info(String summary, T t) {
        logList.forEach(it -> silence(() -> it.info(summary, t)));
    }

    public <T> void info(String summary, String message, T t) {
        logList.forEach(it -> silence(() -> it.info(summary, message, t)));
    }

    public void debug(String message) {
        logList.forEach(it -> silence(() -> it.debug(message)));
    }

    public <T> void debug(T t) {
        logList.forEach(it -> silence(() -> it.debug(t)));
    }

    public void debug(String summary, String message) {
        logList.forEach(it -> silence(() -> it.debug(summary, message)));
    }

    public <T> void debug(String summary, T t) {
        logList.forEach(it -> silence(() -> it.debug(summary, t)));
    }

    public <T> void debug(String summary, String message, T t) {
        logList.forEach(it -> silence(() -> it.debug(summary, message, t)));
    }

    public void error(String message) {
        logList.forEach(it -> silence(() -> it.error(message)));
    }

    public void error(Throwable e) {
        logList.forEach(it -> silence(() -> it.error(e)));
    }

    public void error(String summary, Throwable e) {
        logList.forEach(it -> silence(() -> it.error(summary, e)));
    }

    public void error(String summary, String message) {
        logList.forEach(it -> silence(() -> it.error(summary, message)));
    }

    public void error(String summary, String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.error(summary, message, e)));
    }

    public <T> void error(String summary, T t) {
        logList.forEach(it -> silence(() -> it.error(summary, t)));
    }

    public <T> void error(String summary, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.error(summary, t, e)));
    }

    public <T> void error(String summary, String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.error(summary, message, t, e)));
    }

    public void warn(String message) {
        logList.forEach(it -> silence(() -> it.warn(message)));
    }

    public void warn(Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(e)));
    }

    public void warn(String summary, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(summary, e)));
    }

    public void warn(String summary, String message) {
        logList.forEach(it -> silence(() -> it.warn(summary, message)));
    }

    public void warn(String summary, String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(summary, message, e)));
    }

    public <T> void warn(String summary, T t) {
        logList.forEach(it -> silence(() -> it.warn(summary, t)));
    }

    public <T> void warn(String summary, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(summary, t, e)));
    }

    public <T> void warn(String summary, String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(summary, message, t, e)));
    }

    public void fatal(String message) {
        logList.forEach(it -> silence(() -> it.fatal(message)));
    }

    public void fatal(Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(e)));
    }

    public void fatal(String summary, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, e)));
    }

    public void fatal(String summary, String message) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message)));
    }

    public void fatal(String summary, String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message, e)));
    }

    public <T> void fatal(String summary, T t) {
        logList.forEach(it -> silence(() -> it.fatal(summary, t)));
    }

    public <T> void fatal(String summary, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, t, e)));
    }

    public <T> void fatal(String summary, String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message, t, e)));
    }

}
