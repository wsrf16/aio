package com.aio.portable.swiss.suite.eventbus.bus;


import com.aio.portable.swiss.suite.eventbus.bus.persistence.EventBusPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.group.EventGroup;
import com.aio.portable.swiss.suite.eventbus.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;
import java.util.stream.Collectors;


public class EventBus extends AbstractEventBus {
    private final static String EMPTY = "";
    volatile static EventBus instance;

    @JsonIgnore
    NodePersistence nodePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public void setNodePersistence(NodePersistence nodePersistence) {
        this.nodePersistence = nodePersistence;
        this.persistentContainer = PersistentContainer.buildEventBusPersistentContainer(nodePersistence);
    }

    public NodePersistence getNodePersistence() {
        return nodePersistence;
    }

    public static void initPersistent(NodePersistence nodePersistence) {
        instance = instance != null ? instance : new EventBus(nodePersistence);
    }

    public synchronized static EventBus singletonInstance() {
        if (instance != null)
            return instance;
        else
            throw new RuntimeException("Please initial the EventBus first.");
    }

    public EventBus(NodePersistence nodePersistence) {
        setNodePersistence(nodePersistence);
    }

    private String[] spellTable() {
        return new String[]{EventBusConfig.EVENT_BUS_TABLE};
    }


    public void add(String group) {
        EventGroup eventGroup = buildEventGroup(group);
        addIfNotExists(eventGroup);
    }

    @Override
    public void add(EventGroup eventGroup) {
        addIfNotExists(eventGroup);
    }

    private EventGroup addIfNotExists(EventGroup eventGroup) {
        String group = eventGroup.getGroup();
        if (exists(group)) {
            eventGroup = get(group);
        } else {
            eventGroup = persist(eventGroup);
            String[] tables = spellTable();
            if (!exists(tables[0]))
                persistentContainer.setTable(tables[0], this);
            persistentContainer.setTable(group, eventGroup, tables);
        }
        return eventGroup;
    }

    @Override
    public void remove(String group) {
        String[] tables = spellTable();
        EventGroup eventGroup = get(group);
        eventGroup.clear();

        persistentContainer.removeTable(group, tables);
    }

    @Override
    public void clear() {
        String[] tables = spellTable();
        collection().entrySet().forEach(c -> {
            c.getValue().clear();
            remove(c.getValue().getGroup());
        });

        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public boolean exists(String group) {
        String[] tables = spellTable();
        return persistentContainer.existsTable(group, tables);
    }

    @Override
    public EventGroup get(String group) {
        String[] tables = spellTable();
        EventGroup eventGroup = persistentContainer.getTable(group, EventGroup.class, tables);
        persist(eventGroup);
        return eventGroup;
    }

    @Override
    public Map<String, EventGroup> collection() {
        String[] tables = spellTable();
        return persistentContainer.getAllTable(EMPTY, EventGroup.class, tables)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

    @Override
    public <E extends Event> void send(final E event) {
        this.collection().values().stream().filter(eventGroup -> eventGroup.exists()).forEach(eventGroup -> {
            eventGroup.send(event);
        });
    }


    public EventGroup buildEventGroup(String group) {
        EventGroup eventGroup = new EventGroup(this.nodePersistence, group);
        return eventGroup;
    }

    public EventListener buildEventListener(String group, String listener) {
        EventGroup eventGroup = buildEventGroup(group);
        EventListener eventListener = eventGroup.buildEventListener(listener);
        return eventListener;
    }

    public EventGroup persist(EventGroup eventGroup) {
        eventGroup.setNodePersistence(this.nodePersistence);
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
        eventListener.setNodePersistence(this.nodePersistence);
        return eventListener;
    }







}
