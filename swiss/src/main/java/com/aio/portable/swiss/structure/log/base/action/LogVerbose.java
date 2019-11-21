package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Anything and everything you might want to know about a running block of code.
 */
public interface LogVerbose {
    void verbose(String verbose);

    default void verbose(String verbose, Object[] arguments) {
        verbose = RegexSugar.replace("\\{\\}", verbose, arguments);
        verbose(verbose);
    }

    <T> void verbose(T t);

    void verbose(String summary, String verbose);

    default void verbose(String summary, String verbose, Object[] arguments) {
        verbose = RegexSugar.replace("\\{\\}", verbose, arguments);
        verbose(summary, verbose);
    }

    <T> void verbose(String summary, T t);

    default void v(String verbose) {
        verbose(verbose);
    }

    default void v(String verbose, Object[] arguments) {
        verbose(verbose, arguments);
    }

    default <T> void v(T t) {
        verbose(t);
    }

    default void v(String summary, String verbose) {
        verbose(summary, verbose);
    }

    default void v(String summary, String verbose, Object[] arguments) {
        verbose(summary, verbose, arguments);
    }

    default <T> void v(String summary, T t) {
        verbose(summary, t);
    }
}