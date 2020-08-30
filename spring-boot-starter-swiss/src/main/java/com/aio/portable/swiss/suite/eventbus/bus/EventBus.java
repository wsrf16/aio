package com.aio.portable.swiss.suite.eventbus.bus;


import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.group.EventGroup;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.exception.NotExistEventGroupException;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
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

    private String[] spellTables() {
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

    private void set(EventGroup eventGroup) {
        String group = eventGroup.getGroup();
        if (StringUtils.isEmpty(group)) {
            throw new IllegalArgumentException("group is empty.");
        }
        if (exists(group)) {
            persist(eventGroup);
            String[] tables = spellTables();
            persistentContainer.setTable(group, eventGroup, tables);
        } else {
            throw new NotExistEventGroupException(MessageFormat.format("group {0} is not exist.", eventGroup.getGroup()));
        }
    }

    private EventGroup addIfNotExists(EventGroup eventGroup) {
        String group = eventGroup.getGroup();
        if (exists(group)) {
            eventGroup = get(group);
        } else {
            eventGroup = persist(eventGroup);
            String[] tables = spellTables();
            if (!exists(tables[0]))
                persistentContainer.setTable(tables[0], this);
            persistentContainer.setTable(group, eventGroup, tables);
        }
        return eventGroup;
    }

    @Override
    public void remove(String group) {
        String[] tables = spellTables();
        EventGroup eventGroup = get(group);
        eventGroup.clear();

        persistentContainer.removeTable(group, tables);
    }

    @Override
    public void clear() {
        String[] tables = spellTables();
        collection().entrySet().forEach(c -> {
            c.getValue().clear();
            remove(c.getValue().getGroup());
        });

        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public boolean exists(String group) {
        String[] tables = spellTables();
        return persistentContainer.existsTable(group, tables);
    }

    @Override
    public EventGroup get(String group) {
        String[] tables = spellTables();
        EventGroup eventGroup = persistentContainer.getTable(group, EventGroup.class, tables);
        persist(eventGroup);
        return eventGroup;
    }

    public void enable(String group, boolean enabled) {
        EventGroup eventGroup = get(group);
        eventGroup.setEnabled(enabled);
        set(eventGroup);
    }

    @Override
    public Map<String, EventGroup> collection() {
        String[] tables = spellTables();
        return persistentContainer.getAllTable(EMPTY, EventGroup.class, tables)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

    @Override
    public <E extends Event> Map<String, EventSubscriber> send(final E event) {
        Map<String, EventSubscriber> map = new HashMap<>();

        this.collection()
                .values()
                .stream()
                .filter(eventGroup -> eventGroup.exists() && eventGroup.isEnabled())
                .forEach(eventGroup -> {
            if (CollectionSugar.isEmpty(event.getGroups())) {
                map.putAll(eventGroup.send(event));
            } else if (!CollectionSugar.isEmpty(event.getGroups()) && event.getGroups().contains(eventGroup.getGroup())) {
                map.putAll(eventGroup.send(event));
            }
        });

        return map;
    }


    public EventGroup buildEventGroup(String group) {
        EventGroup eventGroup = new EventGroup(this.nodePersistence, group);
        return eventGroup;
    }

    public EventSubscriber buildEventSubscriber(String group, String subscriber) {
        EventGroup eventGroup = buildEventGroup(group);
        EventSubscriber eventSubscriber = eventGroup.buildEventSubscriber(subscriber);
        return eventSubscriber;
    }

    public EventGroup persist(EventGroup eventGroup) {
        eventGroup.setNodePersistence(this.nodePersistence);
        return eventGroup;
    }

    public EventSubscriber addEventSubscriber(String group, String subscriber) {
        EventGroup eventGroup = buildEventGroup(group);
        EventSubscriber eventSubscriber = eventGroup.buildEventSubscriber(subscriber);
        add(eventGroup);
        eventGroup.add(eventSubscriber);
        return eventSubscriber;
    }

    public EventSubscriber persist(EventSubscriber eventSubscriber) {
        eventSubscriber.setNodePersistence(this.nodePersistence);
        return eventSubscriber;
    }







}
