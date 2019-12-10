package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Internal system events that aren't necessarily observable from the outside.
 */
public interface LogDebug {
    void debug(String message);

    default void debug(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        debug(message);
    }

    <T> void debug(T t);

    void debug(String summary, String message);

    default void debug(String summary, String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        debug(summary, message);
    }

    <T> void debug(String summary, T t);

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
}
