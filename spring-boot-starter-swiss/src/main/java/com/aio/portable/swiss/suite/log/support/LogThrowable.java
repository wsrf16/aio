package com.aio.portable.swiss.suite.log.support;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogThrowable {
    public LogThrowable(Throwable e) {
        setException(e);
    }

    public static final LogThrowable build(Throwable e) {
        return e == null ? null : new LogThrowable(e);
    }

    public void setException(Throwable e) {
        this.message = e.toString();
        this.stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }

    private String message;
    private List<String> stackTrace;
}
