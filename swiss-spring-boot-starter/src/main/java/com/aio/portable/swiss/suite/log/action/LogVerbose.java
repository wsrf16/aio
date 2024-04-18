package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.type.StringSugar;

/**
 * Anything and everything you might want to know about a running block of code.
 */
public interface LogVerbose {
    void verb(String message);

    default void verb(String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        verb(message);
    }

    <T> void verb(T t);

    void verb(String summary, String message);

    default void verb(String summary, String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        verb(summary, message);
    }

    <T> void verb(String summary, T t);

    <T> void verb(String summary, String message, T t);

    default void v(String message) {
        verb(message);
    }

    default void v(String message, Object[] arguments) {
        verb(message, arguments);
    }

    default <T> void v(T t) {
        verb(t);
    }

    default void v(String summary, String message) {
        verb(summary, message);
    }

    default void v(String summary, String message, Object[] arguments) {
        verb(summary, message, arguments);
    }

    default <T> void v(String summary, T t) {
        verb(summary, t);
    }

    default <T> void v(String summary, String message, T t) {
        verb(summary, message, t);
    }
}