package com.aio.portable.swiss.assist.log.base.action;

/**
 * Internal system events that aren't necessarily observable from the outside.
 */
public interface LogDebug {
    void debug(String debug);

    <T> void debug(T t);

    void debug(String summary, String debug);

    <T> void debug(String summary, T t);

    default void d(String debug) {
        debug(debug);
    }

    default <T> void d(T t) {
        debug(t);
    }

    default void d(String summary, String debug) {
        debug(summary, debug);
    }

    default <T> void d(String summary, T t) {
        debug(summary, t);
    }
}
