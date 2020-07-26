package com.aio.portable.swiss.suite.eventbus.event;

import java.util.ArrayList;
import java.util.List;

public class Event extends AbstractEvent {
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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
