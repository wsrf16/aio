package com.aio.portable.swiss.suite.eventbus.listener;

import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.listener.persistence.NodeEventListenerPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.eventbus.subscriber.RestTemplateSubscriber;
import com.aio.portable.swiss.suite.eventbus.subscriber.Subscriber;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class EventListener extends AbstractEventListener {
    @JsonIgnore
    protected boolean concurrent = true;

    @JsonIgnore
    KeyValuePersistence keyValuePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public KeyValuePersistence getKeyValuePersistence() {
        return keyValuePersistence;
    }

    public void setKeyValuePersistence(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        this.persistentContainer = PersistentContainer.buildEventListenerPersistentContainer(keyValuePersistence);
    }

    public EventListener() {
    }

    private String spellTable(String group, String name) {
        String table;
        if (persistentContainer instanceof NodeEventListenerPersistentContainer) {
            table = MessageFormat.format("{0}/{1}/{2}", EventBusConfig.EVENT_BUS_TABLE, getGroup(), getListener());
        } else {
            table = MessageFormat.format("{0}-{1}", getGroup(), getListener());
        }
        return table;
    }

    public EventListener(@NotNull KeyValuePersistence keyValuePersistence, @NotNull String group, @NotNull String name) {
        super(group, name);
        setKeyValuePersistence(keyValuePersistence);
    }

    @Override
    public void add(Subscriber subscriber) {
        String table = spellTable(getGroup(), getListener());
        String key = subscriber.getName();
        persistentContainer.set(table, key, subscriber);
    }

    @Override
    public void remove(Subscriber subscriber) {
        String table = spellTable(getGroup(), getListener());
        String key = subscriber.getName();
        persistentContainer.remove(table, key);
    }

    @Override
    public void remove(String name) {
        String table = spellTable(getGroup(), getListener());
        String key = name;
        persistentContainer.remove(table, key);
    }

    @Override
    public void clear() {
        String table = spellTable(getGroup(), getListener());
        persistentContainer.clear(table);
    }

    @Override
    public Subscriber get(String name) {
        String table = spellTable(getGroup(), getListener());
        String key = name;
        return persistentContainer.get(table, key, Subscriber.class);
    }

    @Override
    public boolean exists() {
        String table = spellTable(getGroup(), getListener());
        return persistentContainer.existsTable(table);
    }

    @Override
    public boolean exists(String name) {
        String table = spellTable(getGroup(), getListener());
        return persistentContainer.exists(table, name);
    }

    @Override
    public Map<String, Subscriber> collection() {
        String table = spellTable(getGroup(), getListener());
        return persistentContainer.getAll(table, Subscriber.class);
    }

    @Override
    public <E extends Event> void onEvent(E event) {
        dispatch(event);
    }

    private <E extends Event> void dispatch(E event) {
        if (!exists())
            return;

        Stream<Map.Entry<String, Subscriber>> stream = concurrent ?
                this.collection().entrySet().parallelStream()
                : this.collection().entrySet().stream();
        stream.forEach(c -> {
            Subscriber subscriber = c.getValue();
            if (Objects.equals(subscriber.getTag(), event.getTag())) {
                if (subscriber instanceof RestTemplateSubscriber) {
                    Object push = subscriber.push(event);
                } else {
                    Object push = subscriber.push(event);
                }
            }
        });
    }



}