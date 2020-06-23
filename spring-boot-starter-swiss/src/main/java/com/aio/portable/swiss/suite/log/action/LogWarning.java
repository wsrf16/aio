package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.PatternSugar;

/**
 * Service is degraded or endangered.
 */
public interface LogWarning {
    void warn(String message);

    default void warn(String message, Object[] arguments) {
        message = PatternSugar.replace("\\{\\}", message, arguments);
        warn(message);
    }

    void warn(Throwable e);

    void warn(String summary, Throwable e);

    void warn(String summary, String message);

    default void warn(String summary, String message, Object[] arguments) {
        message = PatternSugar.replace("\\{\\}", message, arguments);
        warn(summary, message);
    }

    void warn(String summary, String message, Throwable e);

    <T> void warn(String summary, T t);

    <T> void warn(String summary, T t, Throwable e);

    <T> void warn(String summary, String message, T t, Throwable e);

    default void w(String message) {
        warn(message);
    }

    default void w(String message, Object[] arguments) {
        warn(message, arguments);
    }

    default void w(Throwable e) {
        warn(e);
    }

    default void w(String summary, Throwable e) {
        warn(summary, e);
    }

    default void w(String summary, String message) {
        warn(summary, message);
    }

    default void w(String summary, String message, Object[] arguments) {
        warn(summary, message, arguments);
    }

    default void w(String summary, String message, Throwable e) {
        warn(summary, message, e);
    }

    default <T> void w(String summary, T t) {
        warn(summary, t);
    }

    default <T> void w(String summary, T t, Throwable e) {
        warn(summary, t, e);
    }

    default <T> void w(String summary, String message, T t, Throwable e) {
        warn(summary, message, t, e);
    }
}
