package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.type.StringSugar;

/**
 * Functionality is unavailable, invariants are broken or data is lost.
 */
public interface LogError {
    void error(String message);

    default void error(String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        error(message);
    }

    void error(Throwable e);

    void error(String message, Throwable e);

    void error(String summary, String message);

    default void error(String summary, String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        error(summary, message);
    }

    void error(String summary, String message, Throwable e);

    <T> void error(String summary, String message, T t, Throwable e);

    <T> void error(String message, T t, Throwable e);

    <T> void error(String message, T t);

    default void e(String message) {
        error(message);
    }

    default void e(String message, Object[] arguments) {
        error(message, arguments);
    }

    default void e(Throwable e) {
        error(e);
    }

    default void e(String message, Throwable e) {
        error(message, e);
    }

    default void e(String summary, String message) {
        error(summary, message);
    }

    default void e(String summary, String message, Object[] arguments) {
        error(summary, message, arguments);
    }

    default void e(String summary, String message, Throwable e) {
        error(summary, message, e);
    }

    default <T> void e(String summary, String message, T t, Throwable e) {
        error(summary, message, t, e);
    }

    default <T> void e(String message, T t, Throwable e) {
        error(message, t, e);
    }

    default <T> void e(String message, T t) {
        if (t instanceof Throwable)
            error(message, (Throwable) t);
        else
            error(message, t);
    }
}
