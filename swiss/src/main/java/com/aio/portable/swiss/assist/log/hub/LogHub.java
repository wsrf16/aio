package com.aio.portable.swiss.assist.log.hub;

import com.aio.portable.swiss.assist.log.base.AbstractLogger;
import com.aio.portable.swiss.assist.log.base.LogBodys;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by York on 2017/11/23.
 */
public class LogHub extends LogBodys {
    private List<AbstractLogger> loggers;

    public void setAsync(boolean async) {
        loggers.forEach(c -> c.setAsync(async));
    }

    private LogHub(AbstractLogger logger) {
        loggers = new ArrayList<>();
        loggers.add(logger);
    }

    private LogHub(List<AbstractLogger> loggers) {
        this.loggers = loggers;
    }

    public static LogHub build(List<AbstractLogger> loggers) {
        return new LogHub(loggers);
    }

    public static LogHub build(AbstractLogger... logger) {
        List<AbstractLogger> loggers = logger == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(logger));
        return new LogHub(loggers);
    }

    protected static void throwProviderEmpty() {
        try {
            throw new IllegalArgumentException(MessageFormat.format("{0} is Empty.Please register and try again!", LogHub.class.getTypeName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verify() {
        boolean b = loggers != null && loggers.size() > 0;
        if (!b)
            throwProviderEmpty();
    }

    public void dispose() {
        verify();
        loggers.forEach(it ->
        {
            it.dispose();
            it = null;
        });
    }

    public void verbose(String verbose) {
        verify();
        loggers.forEach(it -> it.verbose(verbose));
    }

    public <T> void verbose(T t) {
        verify();
        loggers.forEach(it -> it.verbose(t));
    }

    public void verbose(String summary, String verbose) {
        verify();
        loggers.forEach(it -> it.verbose(summary, verbose));
    }

    public <T> void verbose(String summary, T t) {
        verify();
        loggers.forEach(it -> it.verbose(summary, t));
    }

    public void trace(String trace) {
        verify();
        loggers.forEach(it -> it.trace(trace));
    }

    public <T> void trace(T t) {
        verify();
        loggers.forEach(it -> it.trace(t));
    }

    public void trace(String summary, String trace) {
        verify();
        loggers.forEach(it -> it.trace(summary, trace));
    }

    public <T> void trace(String summary, T t) {
        verify();
        loggers.forEach(it -> it.trace(summary, t));
    }

    public void info(String info) {
        verify();
        loggers.forEach(it -> it.info(info));
    }

    public <T> void info(T t) {
        verify();
        loggers.forEach(it -> it.info(t));
    }

    public void info(String summary, String info) {
        verify();
        loggers.forEach(it -> it.info(summary, info));
    }

    public <T> void info(String summary, T t) {
        verify();
        loggers.forEach(it -> it.info(summary, t));
    }

    public void debug(String debug) {
        verify();
        loggers.forEach(it -> it.debug(debug));
    }

    public <T> void debug(T t) {
        verify();
        loggers.forEach(it -> it.debug(t));
    }

    public void debug(String summary, String debug) {
        verify();
        loggers.forEach(it -> it.debug(summary, debug));
    }

    public <T> void debug(String summary, T t) {
        verify();
        loggers.forEach(it -> it.debug(summary, t));
    }

    public void error(String error) {
        verify();
        loggers.forEach(it -> it.error(error));
    }

    public void error(Exception e) {
        verify();
        loggers.forEach(it -> it.error(e));
    }

    public void error(String summary, Exception e) {
        verify();
        loggers.forEach(it -> it.error(summary, e));
    }

    public void error(String summary, String error) {
        verify();
        loggers.forEach(it -> it.error(summary, error));
    }

    public void error(String summary, String error, Exception e) {
        verify();
        loggers.forEach(it -> it.error(summary, error, e));
    }

    public <T> void error(String summary, T t) {
        verify();
        loggers.forEach(it -> it.error(summary, t));
    }

    public <T> void error(String summary, T t, Exception e) {
        verify();
        loggers.forEach(it -> it.error(summary, t, e));
    }

    public void warn(String warning) {
        verify();
        loggers.forEach(it -> it.warn(warning));
    }

    public void warn(Exception e) {
        verify();
        loggers.forEach(it -> it.warn(e));
    }

    public void warn(String summary, Exception e) {
        verify();
        loggers.forEach(it -> it.warn(summary, e));
    }

    public void warn(String summary, String warning) {
        verify();
        loggers.forEach(it -> it.warn(summary, warning));
    }

    public void warn(String summary, String warning, Exception e) {
        verify();
        loggers.forEach(it -> it.warn(summary, warning, e));
    }

    public <T> void warn(String summary, T t) {
        verify();
        loggers.forEach(it -> it.warn(summary, t));
    }

    public <T> void warn(String summary, T t, Exception e) {
        verify();
        loggers.forEach(it -> it.warn(summary, t, e));
    }

    public void fatal(String warning) {
        verify();
        loggers.forEach(it -> it.fatal(warning));
    }

    public void fatal(Exception e) {
        verify();
        loggers.forEach(it -> it.fatal(e));
    }

    public void fatal(String summary, Exception e) {
        verify();
        loggers.forEach(it -> it.fatal(summary, e));
    }

    public void fatal(String summary, String fatal) {
        verify();
        loggers.forEach(it -> it.fatal(summary, fatal));
    }

    public void fatal(String summary, String fatal, Exception e) {
        verify();
        loggers.forEach(it -> it.fatal(summary, fatal, e));
    }

    public <T> void fatal(String summary, T t) {
        verify();
        loggers.forEach(it -> it.fatal(summary, t));
    }

    public <T> void fatal(String summary, T t, Exception e) {
        verify();
        loggers.forEach(it -> it.fatal(summary, t, e));
    }
}

