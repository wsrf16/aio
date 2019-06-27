package com.york.portable.swiss.assist.log.base.action;


/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LoggerTrace {
    void trace(String trace);

    <T> void trace(T t);

    void trace(String summary, String trace);

    <T> void trace(String summary, T t);

    default void t(String trace) {
        trace(trace);
    }

    default <T> void t(T t) {
        trace(t);
    }

    default void t(String summary, String trace) {
        trace(summary, trace);
    }

    default <T> void t(String summary, T t) {
        trace(summary, t);
    }
}
