package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.StringSugar;

/**
 * Functionality is unavailable, invariants are broken or data is lost.
 */
public interface LogError {
    void error(String message);

    default void error(String message, Object[] arguments) {
        message = StringSugar.format(message, arguments);
        error(message);
    }

    void error(Throwable e);

    void error(String summary, Throwable e);

    void error(String summary, String message);

    default void error(String summary, String message, Object[] arguments) {
        message = StringSugar.format(message, arguments);
        error(summary, message);
    }

    void error(String summary, String message, Throwable e);

    <T> void error(String summary, String message, T t, Throwable e);

    <T> void error(String summary, T t, Throwable e);

    <T> void error(String summary, T t);

    default void e(String message) {
        error(message);
    }

    default void e(String message, Object[] arguments) {
        error(message, arguments);
    }

    default void e(Throwable e) {
        error(e);
    }

    default void e(String summary, Throwable e) {
        error(summary, e);
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

    default <T> void e(String summary, T t, Throwable e) {
        error(summary, t, e);
    }

    default <T> void e(String summary, T t) {
        error(summary, t);
    }
}
