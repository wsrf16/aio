package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.log.action.LogAction;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LogBundle implements LogAction {
//    private static final Log log = LogFactory.getLog(LogBundle.class);
    private static final LocalLog log = LocalLog.getLog(LogBundle.class);

    protected List<LogSingle> logList;

    public List<LogSingle> getLogList() {
        return logList;
    }

    public void setLogList(List<LogSingle> logList) {
        this.logList = logList;
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

    public List<LogSingle> except(List<LogSingle> logList) {
        return logList;
    }

    public static void silence(Runnable runnable) {
        ThrowableSugar.runIfCatch(runnable, e -> log.warn(e));
    }

    public void verb(String message) {
        getLogList().forEach(it -> silence(() -> it.verb(message)));
    }

    public <T> void verb(T t) {
        getLogList().forEach(it -> silence(() -> it.verb(t)));
    }

    public void verb(String summary, String message) {
        getLogList().forEach(it -> silence(() -> it.verb(summary, message)));
    }

    public <T> void verb(String message, T t) {
        getLogList().forEach(it -> silence(() -> it.verb(message, t)));
    }

    public <T> void verb(String summary, String message, T t) {
        getLogList().forEach(it -> silence(() -> it.verb(summary, message, t)));
    }

    public void trace(String message) {
        getLogList().forEach(it -> silence(() -> it.trace(message)));
    }

    public <T> void trace(T t) {
        getLogList().forEach(it -> silence(() -> it.trace(t)));
    }

    public void trace(String summary, String message) {
        getLogList().forEach(it -> silence(() -> it.trace(summary, message)));
    }

    public <T> void trace(String message, T t) {
        getLogList().forEach(it -> silence(() -> it.trace(message, t)));
    }

    public <T> void trace(String summary, String message, T t) {
        getLogList().forEach(it -> silence(() -> it.trace(summary, message, t)));
    }

    public void info(String message) {
        getLogList().forEach(it -> silence(() -> it.info(message)));
    }

    public <T> void info(T t) {
        getLogList().forEach(it -> silence(() -> it.info(t)));
    }

    public void info(String summary, String message) {
        getLogList().forEach(it -> silence(() -> it.info(summary, message)));
    }

    public <T> void info(String message, T t) {
        getLogList().forEach(it -> silence(() -> it.info(message, t)));
    }

    public <T> void info(String summary, String message, T t) {
        getLogList().forEach(it -> silence(() -> it.info(summary, message, t)));
    }

    public void debug(String message) {
        getLogList().forEach(it -> silence(() -> it.debug(message)));
    }

    public <T> void debug(T t) {
        getLogList().forEach(it -> silence(() -> it.debug(t)));
    }

    public void debug(String summary, String message) {
        getLogList().forEach(it -> silence(() -> it.debug(summary, message)));
    }

    public <T> void debug(String message, T t) {
        getLogList().forEach(it -> silence(() -> it.debug(message, t)));
    }

    public <T> void debug(String summary, String message, T t) {
        getLogList().forEach(it -> silence(() -> it.debug(summary, message, t)));
    }

    public void error(String message) {
        getLogList().forEach(it -> silence(() -> it.error(message)));
    }

    public void error(Throwable e) {
        getLogList().forEach(it -> silence(() -> it.error(e)));
    }

    public void error(String message, Throwable e) {
        getLogList().forEach(it -> silence(() -> it.error(message, e)));
    }

    public void error(String summary, String message) {
        getLogList().forEach(it -> silence(() -> it.error(summary, message)));
    }

    public void error(String summary, String message, Throwable e) {
        getLogList().forEach(it -> silence(() -> it.error(summary, message, e)));
    }

    public <T> void error(String message, T t) {
        logList.forEach(it -> silence(() -> it.error(message, t)));
    }

    public <T> void error(String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.error(message, t, e)));
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

    public void warn(String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(message, e)));
    }

    public void warn(String summary, String message) {
        logList.forEach(it -> silence(() -> it.warn(summary, message)));
    }

    public void warn(String summary, String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(summary, message, e)));
    }

    public <T> void warn(String message, T t) {
        logList.forEach(it -> silence(() -> it.warn(message, t)));
    }

    public <T> void warn(String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.warn(message, t, e)));
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

    public void fatal(String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(message, e)));
    }

    public void fatal(String summary, String message) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message)));
    }

    public void fatal(String summary, String message, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message, e)));
    }

    public <T> void fatal(String message, T t) {
        logList.forEach(it -> silence(() -> it.fatal(message, t)));
    }

    public <T> void fatal(String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(message, t, e)));
    }

    public <T> void fatal(String summary, String message, T t, Throwable e) {
        logList.forEach(it -> silence(() -> it.fatal(summary, message, t, e)));
    }

}
