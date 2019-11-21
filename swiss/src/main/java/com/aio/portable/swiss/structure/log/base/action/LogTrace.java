package com.aio.portable.swiss.structure.log.base.action;


import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LogTrace {
    void trace(String trace);

    default void trace(String trace, Object[] arguments) {
        trace = RegexSugar.replace("\\{\\}", trace, arguments);
        trace(trace);
    }

    <T> void trace(T t);

    void trace(String summary, String trace);

    default void trace(String summary, String trace, Object[] arguments) {
        trace = RegexSugar.replace("\\{\\}", trace, arguments);
        trace(summary, trace);
    }

    <T> void trace(String summary, T t);

    default void t(String trace) {
        trace(trace);
    }

    default void t(String trace, Object[] arguments) {
        trace(trace, arguments);
    }

    default <T> void t(T t) {
        trace(t);
    }

    default void t(String summary, String trace) {
        trace(summary, trace);
    }

    default void t(String summary, String trace, Object[] arguments) {
        trace(summary, trace, arguments);
    }

    default <T> void t(String summary, T t) {
        trace(summary, t);
    }
}
