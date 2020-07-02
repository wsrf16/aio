package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.StringSugar;

/**
 * If you have a pager, it goes off when one of these occurs.
 */
public interface LogFatal {
    void fatal(String message);

    default void fatal(String message, Object[] arguments) {
        message = StringSugar.format(message, arguments);
        fatal(message);
    }

    void fatal(Throwable e);

    void fatal(String summary, Throwable e);

    void fatal(String summary, String message);

    default void fatal(String summary, String message, Object[] arguments) {
        message = StringSugar.format(message, arguments);
        fatal(summary, message);
    }

    void fatal(String summary, String message, Throwable e);

    <T> void fatal(String summary, T t);

    <T> void fatal(String summary, T t, Throwable e);

    <T> void fatal(String summary, String message, T t, Throwable e);

    default void f(String message) {
        fatal(message);
    }

    default void f(String message, Object[] arguments) {
        fatal(message, arguments);
    }

    default void f(Throwable e) {
        fatal(e);
    }

    default void f(String summary, Throwable e) {
        fatal(summary, e);
    }

    default void f(String summary, String message) {
        fatal(summary, message);
    }

    default void f(String summary, String message, Object[] arguments) {
        fatal(summary, message, arguments);
    }

    default void f(String summary, String message, Throwable e) {
        fatal(summary, message, e);
    }

    default <T> void f(String summary, T t) {
        fatal(summary, t);
    }

    default <T> void f(String summary, T t, Throwable e) {
        fatal(summary, t, e);
    }

    default <T> void f(String summary, String message, T t, Throwable e) {
        fatal(summary, message, t, e);
    }
}
