package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.type.StringSugar;

/**
 * Service is degraded or endangered.
 */
public interface LogWarning {
    void warn(String message);

    default void warn(String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        warn(message);
    }

    void warn(Throwable e);

    void warn(String message, Throwable e);

    void warn(String summary, String message);

    default void warn(String summary, String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        warn(summary, message);
    }

    void warn(String summary, String message, Throwable e);

    <T> void warn(String message, T t);

    <T> void warn(String message, T t, Throwable e);

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

    default void w(String message, Throwable e) {
        warn(message, e);
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

    default <T> void w(String message, T t) {
        warn(message, t);
    }

    default <T> void w(String message, T t, Throwable e) {
        warn(message, t, e);
    }

    default <T> void w(String summary, String message, T t, Throwable e) {
        warn(summary, message, t, e);
    }
}
