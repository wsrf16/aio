package com.aio.portable.swiss.suite.log.action;


import com.aio.portable.swiss.sugar.type.StringSugar;

/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LogInformation {
    void info(String message);

    default void info(String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        info(message);
    }

    <T> void info(T t);

    void info(String summary, String message);

    default void info(String summary, String message, Object[] arguments) {
        if (arguments != null)
            message = StringSugar.format(message, arguments);
        info(summary, message);
    }

    <T> void info(String summary, T t);

    <T> void info(String summary, String message, T t);

    default void i(String message) {
        info(message);
    }

    default void i(String message, Object[] arguments) {
        info(message, arguments);
    }

    default <T> void i(T t) {
        info(t);
    }

    default void i(String summary, String message) {
        info(summary, message);
    }

    default void i(String summary, String message, Object[] arguments) {
        info(summary, message, arguments);
    }

    default <T> void i(String summary, T t) {
        info(summary, t);
    }

    default <T> void i(String summary, String message, T t) {
        info(summary, message, t);
    }
}
