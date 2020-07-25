package com.aio.portable.swiss.suite.eventbus.group;

import com.aio.portable.swiss.suite.eventbus.group.persistence.NodeEventGroupPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Map;
import java.util.stream.Collectors;

public class EventGroup extends AbstractEventGroup {
    @JsonIgnore
    KeyValuePersistence keyValuePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public KeyValuePersistence getKeyValuePersistence() {
        return keyValuePersistence;
    }

    public void setKeyValuePersistence(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        this.persistentContainer = PersistentContainer.buildEventGroupPersistentContainer(keyValuePersistence);
    }

    public PersistentContainer getPersistentContainer() {
        return persistentContainer;
    }

    public EventGroup() {
    }

    public EventGroup(@NotNull KeyValuePersistence keyValuePersistence, @NotNull String group) {
        super(group);
        setKeyValuePersistence(keyValuePersistence);
    }

    private String spellTable(String group) {
        String table;
        if (persistentContainer instanceof NodeEventGroupPersistentContainer) {
            table = MessageFormat.format("{0}/{1}", EventBusConfig.EVENT_BUS_TABLE, group);
        } else {
            table = MessageFormat.format("{0}", group);
        }
        return table;
    }

    public void add(String listener) {
        EventListener eventListener = buildEventListener(listener);
        add(eventListener);
    }

    @Override
    public void add(EventListener eventListener) {
        addIfNotExists(eventListener);
    }

    private void set(EventListener eventListener) {
//        if (StringUtils.isEmpty(listener.getGroup())) {
//            throw new IllegalArgumentException("listener.group is null");
//        }
//        if (StringUtils.isEmpty(this.getGroup())) {
//            setGroup(listener.getGroup());
//        }
//        if (!StringUtils.isEmpty(this.getGroup()) && !StringUtils.isEmpty(listener.getGroup())) {
//            if (!Objects.equals(this.getGroup(), listener.getGroup()))
//                throw new RuntimeException(MessageFormat.format("listener.group({0}) is different from listenerGroup.group({1}).", listener.getGroup(), this.getGroup()));
//        }
        if (StringUtils.isEmpty(this.getGroup())) {
            throw new IllegalArgumentException("eventGroup.group is null.");
        }
        String table = spellTable(getGroup());
        String key = eventListener.getListener();
        persist(eventListener);
        persistentContainer.set(table, key, eventListener);
    }

//    public EventListener addIfNotExists(String listener) {
//        EventListener eventListener;
//        if (exists(listener)) {
//            eventListener = get(listener);
//        } else {
//            eventListener = buildEventListener(listener);
//            set(eventListener);
//        }
//        return eventListener;
//    }

    public EventListener addIfNotExists(EventListener eventListener) {
        set(eventListener);
        return eventListener;
    }

    @Override
    public void remove(String listener) {
        EventListener eventListener = get(listener);
        eventListener.clear();

        String table = spellTable(getGroup());
        String key = listener;
        persistentContainer.remove(table, key);
    }

    @Override
    public void remove(EventListener eventListener) {
        persist(eventListener);
        eventListener.clear();

        String table = spellTable(getGroup());
        String key = eventListener.getListener();
        persistentContainer.remove(table, key);
    }

    @Override
    public void clear() {
        collection().entrySet().forEach(c -> c.getValue().clear());

        String table = spellTable(getGroup());
        persistentContainer.clear(table);
    }

    @Override
    public EventListener get(String listener) {
        String table = spellTable(getGroup());
        String key = listener;
        EventListener eventListener = persistentContainer.get(table, key, EventListener.class);
        persist(eventListener);
        return eventListener;
    }

    @Override
    public boolean exists(String listener) {
        String table = spellTable(getGroup());
        String key = listener;
        return persistentContainer.exists(table, key);
    }

    @Override
    public boolean exists() {
        String table = spellTable(getGroup());
        return persistentContainer.existsTable(table);
    }

    @Override
    public Map<String, EventListener> collection() {
        String table = spellTable(getGroup());
        return persistentContainer.getAll(table, EventListener.class)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

    public EventListener buildEventListener(String listener) {
        String group = getGroup();
        EventListener eventListener = new EventListener(this.keyValuePersistence, group, listener);
        return eventListener;
    }

    public EventListener persist(EventListener eventListener) {
        eventListener.setKeyValuePersistence(this.keyValuePersistence);
        return eventListener;
    }
}
