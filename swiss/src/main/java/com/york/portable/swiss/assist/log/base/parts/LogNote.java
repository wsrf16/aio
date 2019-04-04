package com.york.portable.swiss.assist.log.base.parts;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by York on 2017/11/22.
 */
public class LogNote {
    @JsonProperty("level")
    public String level;
    @JsonProperty("name")
    public String name;
    @JsonProperty("summary")
    public String summary;
    @JsonProperty("message")
    public String message;
    @JsonProperty("data")
    public Object data;
    @JsonProperty("exception")
    public LogException exception;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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

    public LogException getException() {
        return exception;
    }

    public void setException(LogException exception) {
        this.exception = exception;
    }
}

