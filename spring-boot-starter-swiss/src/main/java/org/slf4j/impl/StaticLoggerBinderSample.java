/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package org.slf4j.impl;

import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinderSample implements LoggerFactoryBinder {

    @Override
    public ILoggerFactory getLoggerFactory() {
        return new LoggerFactory();
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return null;
    }

    public static StaticLoggerBinderSample getSingleton() {
        return new StaticLoggerBinderSample();
    }



    public class LoggerFactory implements ILoggerFactory {

        @Override
        public Logger getLogger(String name) {
            return new Logger2Hub(name);
        }
    }

    public class Logger2Hub extends LogHub implements Logger {
        private String name;

        public Logger2Hub(String name) {
            this.name = name;
        }


        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isTraceEnabled() {
            return false;
        }

        @Override
        public void trace(String msg) {
            super.trace(msg);
        }

        @Override
        public void trace(String format, Object arg) {
            super.trace(format, StringSugar.toString(arg));
        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {
            super.trace(format, new Object[]{arg1, arg2});
        }

        @Override
        public void trace(String format, Object... arguments) {
            super.trace(format, (Object[])arguments);
        }

        @Override
        public void trace(String msg, Throwable t) {
            super.trace(msg, ThrowableSugar.getStackTraceAsString(t));
        }

        @Override
        public boolean isTraceEnabled(Marker marker) {
            return false;
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
            return false;
        }

        @Override
        public void debug(String msg) {
            super.debug(msg);
        }

        @Override
        public void debug(String format, Object arg) {
            super.debug(format, StringSugar.toString(arg));
        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {
            super.debug(format, new Object[]{arg1, arg2});
        }

        @Override
        public void debug(String format, Object... arguments) {
            super.debug(format, (Object[])arguments);
        }

        @Override
        public void debug(String msg, Throwable t) {
            super.debug(msg, ThrowableSugar.getStackTraceAsString(t));
        }

        @Override
        public boolean isDebugEnabled(Marker marker) {
            return false;
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
            return true;
        }

        @Override
        public void info(String msg) {
            super.info(msg);
        }

        @Override
        public void info(String format, Object arg) {
            super.info(format, StringSugar.toString(arg));
        }

        @Override
        public void info(String format, Object arg1, Object arg2) {
            super.info(format, new Object[]{arg1, arg2});
        }

        @Override
        public void info(String format, Object... arguments) {
            super.info(format, (Object[])arguments);
        }

        @Override
        public void info(String msg, Throwable t) {
            super.info(msg, ThrowableSugar.getStackTraceAsString(t));
        }

        @Override
        public boolean isInfoEnabled(Marker marker) {
            return false;
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
            return false;
        }

        @Override
        public void warn(String msg) {
            super.warn(msg);
        }

        @Override
        public void warn(String format, Object arg) {
            super.warn(format, StringSugar.toString(arg));
        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {
            super.warn(format, new Object[]{arg1, arg2});
        }

        @Override
        public void warn(String format, Object... arguments) {
            super.warn(format, (Object[])arguments);
        }

        @Override
        public void warn(String msg, Throwable t) {
            super.warn(msg, ThrowableSugar.getStackTraceAsString(t));
        }

        @Override
        public boolean isWarnEnabled(Marker marker) {
            return false;
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
            return false;
        }

        @Override
        public void error(String msg) {
            super.error(msg);
        }

        @Override
        public void error(String format, Object arg) {
            super.error(format, StringSugar.toString(arg));
        }

        @Override
        public void error(String format, Object arg1, Object arg2) {
            super.error(format, new Object[]{arg1, arg2});
        }

        @Override
        public void error(String format, Object... arguments) {
            super.error(format, (Object[])arguments);
        }

        @Override
        public void error(String msg, Throwable t) {
            super.error(msg, ThrowableSugar.getStackTraceAsString(t));
        }

        @Override
        public boolean isErrorEnabled(Marker marker) {
            return false;
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
}
