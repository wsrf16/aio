package com.aio.portable.swiss.suite.log.support;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by York on 2017/11/22.
 */
public class StandardLogNote implements LogNote {
    @JsonProperty("level")
    public LevelEnum level;
    @JsonProperty("name")
    public String name;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public Object data;
    @JsonProperty("exception")
    public LogThrowable exception;
    @JsonProperty("outputType")
    public String outputType;


    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LogThrowable getException() {
        return exception;
    }

    public void setException(LogThrowable exception) {
        this.exception = exception;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }
}

