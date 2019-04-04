package com.york.portable.swiss.assist.log.hub;

import com.york.portable.swiss.assist.log.base.AbsLogger;
import com.york.portable.swiss.assist.log.base.ILogger;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by York on 2017/11/23.
 */
public class LoggerHub implements ILoggerHub {
    List<ILogger> loggers;

    public void setAsync(boolean async) {
        loggers.stream().forEach(c -> ((AbsLogger)c).setAsync(async));
    }

    public LoggerHub() {
        loggers = new ArrayList<>();
    }

    public LoggerHub(ILogger logger) {
        loggers = new ArrayList<>();
        addRegister(logger);
    }

    public LoggerHub(List<ILogger> loggers) {
        this.loggers = loggers;
    }

    public void addRegister(ILogger logger) {
        loggers.add(logger);
    }

    public static LoggerHub build(ILogger... logger) {
        List<ILogger> loggers = logger == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(logger));
        return new LoggerHub(loggers);
    }

//    public static LoggerHub buildAsync(ILogger... logger) {
//        LoggerHub loggerHub = build(logger);
//        loggerHub.setAsync(true);
//        return loggerHub;
//    }

//    public static LoggerHub buildLog4j2(Class clazz){
//        LoggerHub logger = LoggerHub.build();
//        logger.addRegister(ConsoleLogger.build(clazz));
//        logger.addRegister(Log4j2Logger.build(clazz));
//        return logger;
//    }
//
//    public static LoggerHub buildLogback(Class clazz){
//        LoggerHub logger = LoggerHub.build();
//        logger.addRegister(ConsoleLogger.build(clazz));
//        logger.addRegister(Slf4jLogger.build(clazz));
//        return logger;
//    }
//
//    public static LoggerHub buildFile(Class clazz){
//        LoggerHub logger = LoggerHub.build();
//        logger.addRegister(ConsoleLogger.build(clazz));
//        logger.addRegister(FileLogger.build(clazz));
//        return logger;
//    }

    protected static void throwProviderEmpty() {
        try {
            throw new IllegalArgumentException(MessageFormat.format("{0} is Empty.Please register and try again!", LoggerHub.class.getTypeName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it ->
            {
                it.dispose();
                it = null;
            });
    }

    public void verbose(String verbose) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(verbose));
        else
            throwProviderEmpty();
    }

    public <T> void verbose(T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(t));
        else
            throwProviderEmpty();
    }

    public void verbose(String summary, String verbose) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(summary, verbose));
        else
            throwProviderEmpty();
    }

    public <T> void verbose(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(summary, t));
        else
            throwProviderEmpty();
    }

    public void trace(String trace) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.trace(trace));
        else
            throwProviderEmpty();
    }

    public <T> void trace(T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.trace(t));
        else
            throwProviderEmpty();
    }

    public void trace(String summary, String trace) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.trace(summary, trace));
        else
            throwProviderEmpty();
    }

    public <T> void trace(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.trace(summary, t));
        else
            throwProviderEmpty();
    }

    public void info(String info) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(info));
        else
            throwProviderEmpty();
    }

    public <T> void info(T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(t));
        else
            throwProviderEmpty();
    }

    public void info(String summary, String info) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(summary, info));
        else
            throwProviderEmpty();
    }

    public <T> void info(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.info(summary, t));
        else
            throwProviderEmpty();
    }

    public void debug(String debug) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.debug(debug));
        else
            throwProviderEmpty();
    }

    public <T> void debug(T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.debug(t));
        else
            throwProviderEmpty();
    }

    public void debug(String summary, String debug) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.debug(summary, debug));
        else
            throwProviderEmpty();
    }

    public <T> void debug(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.debug(summary, t));
        else
            throwProviderEmpty();
    }

    public void error(String error) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(error));
        else
            throwProviderEmpty();
    }

    public void error(Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(e));
        else
            throwProviderEmpty();
    }

    public void error(String summary, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(summary, e));
        else
            throwProviderEmpty();
    }

    public void error(String summary, String error) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(summary, error));
        else
            throwProviderEmpty();
    }

    public void error(String summary, String error, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(summary, error, e));
        else
            throwProviderEmpty();
    }

    public <T> void error(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(summary, t));
        else
            throwProviderEmpty();
    }

    public <T> void error(String summary, T t, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.error(summary, t, e));
        else
            throwProviderEmpty();
    }

    public void warn(String warning) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(warning));
        else
            throwProviderEmpty();
    }

    public void warn(Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(e));
        else
            throwProviderEmpty();
    }

    public void warn(String summary, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(summary, e));
        else
            throwProviderEmpty();
    }

    public void warn(String summary, String warning) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(summary, warning));
        else
            throwProviderEmpty();
    }

    public void warn(String summary, String warning, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(summary, warning, e));
        else
            throwProviderEmpty();
    }

    public <T> void warn(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(summary, t));
        else
            throwProviderEmpty();
    }

    public <T> void warn(String summary, T t, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.warn(summary, t, e));
        else
            throwProviderEmpty();
    }

    public void fatal(String warning) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(warning));
        else
            throwProviderEmpty();
    }

    public void fatal(Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(e));
        else
            throwProviderEmpty();
    }

    public void fatal(String summary, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(summary, e));
        else
            throwProviderEmpty();
    }

    public void fatal(String summary, String fatal) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(summary, fatal));
        else
            throwProviderEmpty();
    }

    public void fatal(String summary, String fatal, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(summary, fatal, e));
        else
            throwProviderEmpty();
    }

    public <T> void fatal(String summary, T t) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(summary, t));
        else
            throwProviderEmpty();
    }

    public <T> void fatal(String summary, T t, Exception e) {
        if (loggers != null && loggers.size() > 0)
            loggers.forEach(it -> it.fatal(summary, t, e));
        else
            throwProviderEmpty();
    }
}

