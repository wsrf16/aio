package com.york.portable.swiss.assist.log.base.parts;

import java.util.List;

public class LogException {
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
