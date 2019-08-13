package com.york.portable.swiss.assist.log.base.action;

/**
 * Anything and everything you might want to know about a running block of code.
 */
public interface LogVerbose {
    void verbose(String verbose);

    <T> void verbose(T t);

    void verbose(String summary, String verbose);

    <T> void verbose(String summary, T t);

    default void v(String verbose) {
        verbose(verbose);
    }

    default <T> void v(T t) {
        verbose(t);
    }

    default void v(String summary, String verbose) {
        verbose(summary, verbose);
    }

    default <T> void v(String summary, T t) {
        verbose(summary, t);
    }
}