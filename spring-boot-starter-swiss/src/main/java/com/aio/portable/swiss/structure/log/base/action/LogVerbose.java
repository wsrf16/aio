package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Anything and everything you might want to know about a running block of code.
 */
public interface LogVerbose {
    void verbose(String message);

    default void verbose(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        verbose(message);
    }

    <T> void verbose(T t);

    void verbose(String summary, String message);

    default void verbose(String summary, String verbose, Object[] arguments) {
        verbose = RegexSugar.replace("\\{\\}", verbose, arguments);
        verbose(summary, verbose);
    }

    <T> void verbose(String summary, T t);

    <T> void verbose(String summary, String message, T t);

    default void v(String message) {
        verbose(message);
    }

    default void v(String message, Object[] arguments) {
        verbose(message, arguments);
    }

    default <T> void v(T t) {
        verbose(t);
    }

    default void v(String summary, String message) {
        verbose(summary, message);
    }

    default void v(String summary, String message, Object[] arguments) {
        verbose(summary, message, arguments);
    }

    default <T> void v(String summary, T t) {
        verbose(summary, t);
    }

    default <T> void v(String summary, String message, T t) {
        verbose(summary, message, t);
    }
}