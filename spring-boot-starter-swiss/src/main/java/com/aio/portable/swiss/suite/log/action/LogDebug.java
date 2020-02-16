package com.aio.portable.swiss.suite.log.action;

import com.aio.portable.swiss.sugar.PatternSugar;

/**
 * Internal system events that aren't necessarily observable from the outside.
 */
public interface LogDebug {
    void debug(String message);

    default void debug(String message, Object[] arguments) {
        message = PatternSugar.replace("\\{\\}", message, arguments);
        debug(message);
    }

    <T> void debug(T t);

    void debug(String summary, String message);

    default void debug(String summary, String message, Object[] arguments) {
        message = PatternSugar.replace("\\{\\}", message, arguments);
        debug(summary, message);
    }

    <T> void debug(String summary, T t);

    <T> void debug(String summary, String message, T t);

    default void d(String message) {
        debug(message);
    }

    default void d(String message, Object[] arguments) {
        debug(message, arguments);
    }

    default <T> void d(T t) {
        debug(t);
    }

    default void d(String summary, String message) {
        debug(summary, message);
    }

    default void d(String summary, String message, Object[] arguments) {
        debug(summary, message, arguments);
    }

    default <T> void d(String summary, T t) {
        debug(summary, t);
    }

    default <T> void d(String summary, String message, T t) {
        debug(summary, message, t);
    }
}
