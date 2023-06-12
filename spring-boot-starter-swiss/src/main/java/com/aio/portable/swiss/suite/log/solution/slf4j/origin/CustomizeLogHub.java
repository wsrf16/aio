package com.aio.portable.swiss.suite.log.solution.slf4j.origin;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import org.slf4j.Logger;
import org.slf4j.Marker;

public class CustomizeLogHub implements Logger {
    private String name;

    private LogHub log;

    public CustomizeLogHub(String name) {
        this.name = name;
        this.log = LogHubFactory.staticBuild(name);
    }

    public LevelEnum level() {
        return LogHubProperties.getSingleton().getDefaultLevelIfAbsent();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isTraceEnabled() {
        return level().beMatched(LevelEnum.TRACE);
    }

    @Override
    public void trace(String msg) {
        if (isTraceEnabled())
            log.trace(msg);
    }

    @Override
    public void trace(String format, Object arg) {
        if (isTraceEnabled())
            this.trace(format, new Object[]{arg});
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        if (isTraceEnabled())
            log.trace(format, new Object[]{arg1, arg2});
    }

    @Override
    public void trace(String format, Object... arguments) {
        if (isTraceEnabled())
            log.trace(format, (Object[]) arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        if (isTraceEnabled())
            log.trace(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return level().beMatched(LevelEnum.TRACE);
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
        return level().beMatched(LevelEnum.DEBUG);
    }

    @Override
    public void debug(String msg) {
        if (isDebugEnabled())
            log.debug(msg);
    }

    @Override
    public void debug(String format, Object arg) {
        if (isDebugEnabled())
            this.debug(format, new Object[]{arg});
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        if (isDebugEnabled())
            log.debug(format, new Object[]{arg1, arg2});
    }

    @Override
    public void debug(String format, Object... arguments) {
        if (isDebugEnabled())
            log.debug(format, (Object[]) arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        if (isDebugEnabled())
            log.debug(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return level().beMatched(LevelEnum.DEBUG);
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
        return level().beMatched(LevelEnum.INFO);
    }

    @Override
    public void info(String msg) {
        log.info(msg);
    }

    @Override
    public void info(String format, Object arg) {
        this.info(format, new Object[]{arg});
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log.info(format, new Object[]{arg1, arg2});
    }

    @Override
    public void info(String format, Object... arguments) {
        log.info(format, (Object[]) arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        log.info(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return level().beMatched(LevelEnum.INFO);
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
        return level().beMatched(LevelEnum.WARN);
    }

    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    @Override
    public void warn(String format, Object arg) {
        this.warn(format, new Object[]{arg});
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log.warn(format, new Object[]{arg1, arg2});
    }

    @Override
    public void warn(String format, Object... arguments) {
        log.warn(format, (Object[]) arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        log.warn(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return level().beMatched(LevelEnum.WARN);
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
        return level().beMatched(LevelEnum.ERROR);
    }

    @Override
    public void error(String msg) {
        if (isErrorEnabled())
            log.error(msg);
    }

    @Override
    public void error(String format, Object arg) {
        if (isErrorEnabled())
            this.error(format, new Object[]{arg});
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        if (isErrorEnabled())
            log.error(format, new Object[]{arg1, arg2});
    }

    @Override
    public void error(String format, Object... arguments) {
        if (isErrorEnabled())
            log.error(format, (Object[]) arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        if (isErrorEnabled())
            log.error(msg, ThrowableSugar.getStackTraceAsString(t));
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return level().beMatched(LevelEnum.ERROR);
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
