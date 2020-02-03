package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Functionality is unavailable, invariants are broken or data is lost.
 */
public interface LogError {
    void error(String message);

    default void error(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        error(message);
    }

    void error(Exception e);

    void error(String summary, Exception e);

    void error(String summary, String message);

    default void error(String summary, String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        error(summary, message);
    }

    void error(String summary, String message, Exception e);

    <T> void error(String summary, String message, T t, Exception e);

    <T> void error(String summary, T t, Exception e);

    <T> void error(String summary, T t);

    default void e(String message) {
        error(message);
    }

    default void e(String message, Object[] arguments) {
        error(message, arguments);
    }

    default void e(Exception e) {
        error(e);
    }

    default void e(String summary, Exception e) {
        error(summary, e);
    }

    default void e(String summary, String message) {
        error(summary, message);
    }

    default void e(String summary, String message, Object[] arguments) {
        error(summary, message, arguments);
    }

    default void e(String summary, String message, Exception e) {
        error(summary, message, e);
    }

    default <T> void e(String summary, String message, T t, Exception e) {
        error(summary, message, t, e);
    }

    default <T> void e(String summary, T t, Exception e) {
        error(summary, t, e);
    }

    default <T> void e(String summary, T t) {
        error(summary, t);
    }
}
