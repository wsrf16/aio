package com.aio.portable.swiss.structure.log.base.action;


import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LogInformation {
    void info(String message);

    default void info(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        info(message);
    }

    <T> void info(T t);

    void info(String summary, String message);

    default void info(String summary, String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        info(summary, message);
    }

    <T> void info(String summary, T t);

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
}
