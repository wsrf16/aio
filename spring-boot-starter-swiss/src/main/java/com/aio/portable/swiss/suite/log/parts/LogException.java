package com.aio.portable.swiss.suite.log.parts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogException {
    public LogException(Throwable e) {
        setException(e);
    }

    public final static LogException buildLogException(Throwable e) {
        return new LogException(e);
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
