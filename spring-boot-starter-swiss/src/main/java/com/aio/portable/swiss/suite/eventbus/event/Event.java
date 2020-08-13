package com.aio.portable.swiss.suite.eventbus.event;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * event object
 */
public class Event extends EventObject {
    private List<String> tags;

    private HttpHeaders headers;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Event() {
    }

    public Event(Object source, List<String> tags) {
        super(source);
        this.tags = tags;
    }

    public Event(Object source, String tag) {
        this(source, new ArrayList<String>(){{add(tag);}});
    }
}
