package com.aio.portable.swiss.structure.log.base.action;


import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LogTrace {
    void trace(String message);

    default void trace(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        trace(message);
    }

    <T> void trace(T t);

    void trace(String summary, String message);

    default void trace(String summary, String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        trace(summary, message);
    }

    <T> void trace(String summary, T t);

    default void t(String message) {
        trace(message);
    }

    default void t(String message, Object[] arguments) {
        trace(message, arguments);
    }

    default <T> void t(T t) {
        trace(t);
    }

    default void t(String summary, String message) {
        trace(summary, message);
    }

    default void t(String summary, String message, Object[] arguments) {
        trace(summary, message, arguments);
    }

    default <T> void t(String summary, T t) {
        trace(summary, t);
    }
}
