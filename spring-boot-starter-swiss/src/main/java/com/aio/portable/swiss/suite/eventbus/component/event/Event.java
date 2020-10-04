package com.aio.portable.swiss.suite.eventbus.component.event;

import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

/**
 * event object
 */
public class Event extends EventObject {
    private List<String> topics;

    private List<String> namespaces;

    private HttpHeaders headers;

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public List<String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<String> namespaces) {
        this.namespaces = namespaces;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public Event() {
    }

    public Event(Object source, List<String> topics) {
        super(source);
        this.topics = topics;
    }

    public Event(Object source, String topic) {
        this(source, new ArrayList<String>(){{add(topic);}});
    }
}
