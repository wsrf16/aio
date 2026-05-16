package com.aio.portable.swiss.suite.log.slfj4jadaptor;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.util.List;
import java.util.stream.Collectors;

public class Slf4JAdaptorLogger implements Logger {
    private String name;

    private LogHub logHub;

    public Slf4JAdaptorLogger(String name) {
        this.name = name;
        this.logHub = LogHubFactory.staticBuild(name);
        List<LogSingle> list = this.logHub.getLogList();
        this.logHub.setLogList(list.stream().filter(d -> !(d instanceof Slf4JLog)).collect(Collectors.toList()));
    }

    public LevelEnum getLevel() {
        return LogHubProperties.getSingleton() == null ? LevelEnum.INFO : LogHubProperties.getSingleton().getLevelOrDefault();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return getLevel().beMatched(LevelEnum.TRACE);
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled())
            logHub.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled())
            this.trace(format, new Object[]{arg});
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled())
            logHub.trace(format, new Object[]{arg1, arg2});
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled())
            logHub.trace(format, (Object[]) arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled())
            logHub.trace(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return getLevel().beMatched(LevelEnum.TRACE);
    }

    @Override
    public void trace(Marker marker, String msg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg) {

    }

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void trace(Marker marker, String format, Object... argArray) {

    }

    @Override
    public void trace(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isDebugEnabled() {
        return getLevel().beMatched(LevelEnum.DEBUG);
    }

    @Override
    public void debug(String msg) {
        if (isDebugEnabled())
            logHub.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled())
            this.debug(format, new Object[]{arg});
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled())
            logHub.debug(format, new Object[]{arg1, arg2});
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled())
            logHub.debug(format, (Object[]) arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (isDebugEnabled())
            logHub.debug(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return getLevel().beMatched(LevelEnum.DEBUG);
    }

    @Override
    public void debug(Marker marker, String msg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg) {

    }

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void debug(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void debug(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isInfoEnabled() {
        return getLevel().beMatched(LevelEnum.INFO);
    }

    @Override
    public void info(String msg) {
        if (isInfoEnabled())
            logHub.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        if (isInfoEnabled())
            this.info(format, new Object[]{arg});
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        logHub.info(format, new Object[]{arg1, arg2});
    }

    @Override
    public void info(String format, Object... arguments) {
        if (isInfoEnabled())
            logHub.info(format, (Object[]) arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        if (isInfoEnabled())
            logHub.info(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return getLevel().beMatched(LevelEnum.INFO);
    }

    @Override
    public void info(Marker marker, String msg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg) {

    }

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void info(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void info(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isWarnEnabled() {
        return getLevel().beMatched(LevelEnum.WARN);
    }

    @Override
    public void warn(String msg) {
        if (isWarnEnabled())
            logHub.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        if (isWarnEnabled())
            this.warn(format, new Object[]{arg});
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        if (isWarnEnabled())
            logHub.warn(format, new Object[]{arg1, arg2});
    }

    @Override
    public void warn(String format, Object... arguments) {
        if (isWarnEnabled())
            logHub.warn(format, (Object[]) arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        if (isWarnEnabled())
            logHub.warn(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return getLevel().beMatched(LevelEnum.WARN);
    }

    @Override
    public void warn(Marker marker, String msg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg) {

    }

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void warn(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void warn(Marker marker, String msg, Throwable t) {

    }

    @Override
    public boolean isErrorEnabled() {
        return getLevel().beMatched(LevelEnum.ERROR);
    }

    @Override
    public void error(String msg) {
        if (isErrorEnabled())
            logHub.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled())
            this.error(format, new Object[]{arg});
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled())
            logHub.error(format, new Object[]{arg1, arg2});
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled())
            logHub.error(format, (Object[]) arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (isErrorEnabled())
            logHub.error(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return getLevel().beMatched(LevelEnum.ERROR);
    }

    @Override
    public void error(Marker marker, String msg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg) {

    }

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {

    }

    @Override
    public void error(Marker marker, String format, Object... arguments) {

    }

    @Override
    public void error(Marker marker, String msg, Throwable t) {

    }
}
