package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.type.StringSugar;

/**
 * If you have a pager, it goes off when one of these occurs.
 */
public interface LogFatal {
    void fatal(String message);

    default void fatal(String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        fatal(message);
    }

    void fatal(Throwable e);

    void fatal(String message, Throwable e);

    void fatal(String summary, String message);

    default void fatal(String summary, String message, Object... arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        fatal(summary, message);
    }

    void fatal(String summary, String message, Throwable e);

    <T> void fatal(String message, T t);

    <T> void fatal(String message, T t, Throwable e);

    <T> void fatal(String summary, String message, T t, Throwable e);

    default void f(String message) {
        fatal(message);
    }

    default void f(String message, Object... arguments) {
        fatal(message, arguments);
    }

    default void f(Throwable e) {
        fatal(e);
    }

    default void f(String message, Throwable e) {
        fatal(message, e);
    }

    default void f(String summary, String message) {
        fatal(summary, message);
    }

    default void f(String summary, String message, Object... arguments) {
        fatal(summary, message, arguments);
    }

    default void f(String summary, String message, Throwable e) {
        fatal(summary, message, e);
    }

    default <T> void f(String message, T t) {
        if (t instanceof Throwable)
            fatal(message, (Throwable) t);
        else
            fatal(message, t);
    }

    default <T> void f(String message, T t, Throwable e) {
        fatal(message, t, e);
    }

    default <T> void f(String summary, String message, T t, Throwable e) {
        fatal(summary, message, t, e);
    }
}
