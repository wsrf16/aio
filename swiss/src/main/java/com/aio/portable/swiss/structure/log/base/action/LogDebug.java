package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Internal system events that aren't necessarily observable from the outside.
 */
public interface LogDebug {
    void debug(String debug);

    default void debug(String debug, Object[] arguments) {
        debug = RegexSugar.replace("\\{\\}", debug, arguments);
        debug(debug);
    }

    <T> void debug(T t);

    void debug(String summary, String debug);

    default void debug(String summary, String debug, Object[] arguments) {
        debug = RegexSugar.replace("\\{\\}", debug, arguments);
        debug(summary, debug);
    }

    <T> void debug(String summary, T t);

    default void d(String debug) {
        debug(debug);
    }

    default void d(String debug, Object[] arguments) {
        debug(debug, arguments);
    }

    default <T> void d(T t) {
        debug(t);
    }

    default void d(String summary, String debug) {
        debug(summary, debug);
    }

    default void d(String summary, String debug, Object[] arguments) {
        debug(summary, debug, arguments);
    }

    default <T> void d(String summary, T t) {
        debug(summary, t);
    }
}
