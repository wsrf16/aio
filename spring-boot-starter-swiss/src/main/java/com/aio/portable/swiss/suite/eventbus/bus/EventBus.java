package com.aio.portable.swiss.suite.eventbus.bus;


import com.aio.portable.swiss.suite.eventbus.bus.persistence.KeyValueEventBusPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.bus.persistence.NodeEventBusPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.group.EventGroup;
import com.aio.portable.swiss.suite.eventbus.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.aio.portable.swiss.suite.storage.nosql.zookeeper.ZooKeeperPO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.stream.Collectors;


public class EventBus extends AbstractEventBus {
    volatile static EventBus instance;

    @JsonIgnore
    KeyValuePersistence keyValuePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public void setKeyValuePersistence(KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
        if (keyValuePersistence instanceof ZooKeeperPO) {
            this.persistentContainer = new NodeEventBusPersistentContainer(keyValuePersistence);
        } else {
            this.persistentContainer = new KeyValueEventBusPersistentContainer(keyValuePersistence);
        }
    }

    public KeyValuePersistence getKeyValuePersistence() {
        return keyValuePersistence;
    }

    public static void initPersistent(KeyValuePersistence keyValuePersistence) {
        instance = instance != null ? instance : new EventBus(keyValuePersistence);
    }

    public synchronized static EventBus singletonInstance() {
        if (instance != null)
            return instance;
        else
            throw new RuntimeException("Please initial the EventBus first.");
    }


    public EventBus(KeyValuePersistence keyValuePersistence) {
        setKeyValuePersistence(keyValuePersistence);
    }

    private String spellTable() {
        String table;
        if (persistentContainer instanceof NodeEventBusPersistentContainer) {
            table = EventBusConfig.EVENT_BUS_TABLE;
        } else {
            table = EventBusConfig.EVENT_BUS_TABLE;
        }
        return table;
    }


    public void add(String group) {
//        addIfNotExists(group);
        EventGroup eventGroup = buildEventGroup(group);
        add(eventGroup);
    }

    @Override
    public void add(EventGroup eventGroup) {
        addIfNotExists(eventGroup);
    }

//    private EventGroup addIfNotExists(String group) {
////        EventGroup eventGroup = ClassSugar.newInstance(clazz);
//        EventGroup eventGroup;
//        if (exists(group)) {
//            eventGroup = get(group);
//        } else {
//            eventGroup = buildEventGroup(group);
//            String table = spellTable();
//            persistentContainer.set(table, group, eventGroup);
//        }
//        return eventGroup;
//    }

    private EventGroup addIfNotExists(EventGroup eventGroup) {
        String group = eventGroup.getGroup();
        if (exists(group)) {
            eventGroup = get(group);
        } else {
            eventGroup = persist(eventGroup);
            String table = spellTable();
            persistentContainer.set(table, group, eventGroup);
        }
        return eventGroup;
    }

    @Override
    public void remove(String group) {
        String table = spellTable();
        EventGroup eventGroup = get(group);
        eventGroup.clear();

        persistentContainer.remove(table, group);
    }

    @Override
    public void clear() {
        String table = spellTable();
        collection().entrySet().forEach(c -> c.getValue().clear());

        persistentContainer.clear(table);
    }

    @Override
    public boolean exists(String group) {
        String table = spellTable();
        return persistentContainer.exists(table, group);
    }

    @Override
    public EventGroup get(String group) {
        String table = spellTable();
        EventGroup eventGroup = persistentContainer.get(table, group, EventGroup.class);
        persist(eventGroup);
        return eventGroup;
    }

    @Override
    public Map<String, EventGroup> collection() {
        String table = spellTable();
        return persistentContainer.getAll(table, EventGroup.class)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

//    @Override
//    public void addGroupAndListener(EventListener listener) {
//        String group = listener.getGroup();
//
//        EventGroup eventGroup = addIfNotExists(group);
//        eventGroup.add(listener);
//    }

    @Override
    public <E extends Event> void send(final E event) {
        this.collection().values().stream().filter(eventGroup -> eventGroup.exists()).forEach(eventGroup -> {
            eventGroup.collection().values().stream().parallel().forEach(listener -> {
                send(listener, event);
            });
        });
    }

    private final <E extends Event> void send(EventListener  listener, E event) {
        listener.onEvent(event);
    }


    public EventGroup buildEventGroup(String group) {
        EventGroup eventGroup = new EventGroup(this.keyValuePersistence, group);
        return eventGroup;
    }

    public EventListener buildEventListener(String group, String listener) {
        EventGroup eventGroup = buildEventGroup(group);
        EventListener eventListener = eventGroup.buildEventListener(listener);
        return eventListener;
    }

    public EventGroup persist(EventGroup eventGroup) {
        eventGroup.setKeyValuePersistence(this.keyValuePersistence);
        return eventGroup;
    }

    public EventListener addEventListener(String group, String listener) {
        EventGroup eventGroup = buildEventGroup(group);
        EventListener eventListener = eventGroup.buildEventListener(listener);
        add(eventGroup);
        eventGroup.add(eventListener);
        return eventListener;
    }

    public EventListener persist(EventListener eventListener) {
        eventListener.setKeyValuePersistence(this.keyValuePersistence);
        return eventListener;
    }







}
