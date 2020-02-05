package com.aio.portable.swiss.structure.log.base;

import java.text.MessageFormat;
import java.util.*;

/**
 * Created by York on 2017/11/23.
 */
public class LogHub extends LogHubBody {
//    private static BiConsumer<Boolean, Supplier<Void>> applyIf = (cond, supplier) -> {if (cond) supplier.get()};

    private List<LogSingle> loggers;

    private float samplerRate = 1f;

    public float getSamplerRate() {
        return samplerRate;
    }

    public LogHub setSamplerRate(float samplerRate) {
        this.samplerRate = samplerRate;
        return this;
    }

    public void setAsync(boolean async) {
        loggers.forEach(c -> c.setAsync(async));
    }

    private LogHub(LogSingle logger) {
        loggers = new ArrayList<>();
        loggers.add(logger);
    }

    private LogHub(List<LogSingle> loggers) {
        this.loggers = loggers;
    }

    public static LogHub build(List<LogSingle> loggers) {
        return new LogHub(loggers);
    }

    public static LogHub build(LogSingle... logger) {
        List<LogSingle> loggers = logger == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(logger));
        return new LogHub(loggers);
    }

    protected static void throwProviderEmpty() {
        try {
            throw new IllegalArgumentException(MessageFormat.format("{0} is Empty.Please register and try again!", LogHub.class.getTypeName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void check() {
        boolean b = loggers != null && loggers.size() > 0;
        if (!b)
            throwProviderEmpty();
    }

    private boolean passing() {
        boolean b1 = sampling();
        return b1;
    }

    private boolean sampling() {
        float f = new Random().nextFloat();
        return f <= samplerRate;
    }

    public void dispose() {
        check();
        loggers.forEach(it ->
        {
            it.dispose();
            it = null;
        });
    }

    public void verbose(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.verbose(message));
    }

    public <T> void verbose(T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.verbose(t));
    }

    public void verbose(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.verbose(summary, message));
    }

    public <T> void verbose(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.verbose(summary, t));
    }

    public void trace(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.trace(message));
    }

    public <T> void trace(T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.trace(t));
    }

    public void trace(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.trace(summary, message));
    }

    public <T> void trace(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.trace(summary, t));
    }

    public void info(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.info(message));
    }

    public <T> void info(T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.info(t));
    }

    public void info(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.info(summary, message));
    }

    public <T> void info(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.info(summary, t));
    }

    public void debug(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.debug(message));
    }

    public <T> void debug(T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.debug(t));
    }

    public void debug(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.debug(summary, message));
    }

    public <T> void debug(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.debug(summary, t));
    }

    public void error(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(message));
    }

    public void error(Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(e));
    }

    public void error(String summary, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, e));
    }

    public void error(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, message));
    }

    public void error(String summary, String message, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, message, e));
    }

    public <T> void error(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, t));
    }

    public <T> void error(String summary, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, t, e));
    }

    public <T> void error(String summary, String message, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.error(summary, message, t, e));
    }

    public void warn(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(message));
    }

    public void warn(Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(e));
    }

    public void warn(String summary, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, e));
    }

    public void warn(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, message));
    }

    public void warn(String summary, String message, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, message, e));
    }

    public <T> void warn(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, t));
    }

    public <T> void warn(String summary, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, t, e));
    }

    public <T> void warn(String summary, String message, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.warn(summary, message, t, e));
    }

    public void fatal(String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(message));
    }

    public void fatal(Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(e));
    }

    public void fatal(String summary, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, e));
    }

    public void fatal(String summary, String message) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, message));
    }

    public void fatal(String summary, String message, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, message, e));
    }

    public <T> void fatal(String summary, T t) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, t));
    }

    public <T> void fatal(String summary, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, t, e));
    }

    public <T> void fatal(String summary, String message, T t, Exception e) {
        check();
        if (passing())
            loggers.forEach(it -> it.fatal(summary, message, t, e));
    }
}
