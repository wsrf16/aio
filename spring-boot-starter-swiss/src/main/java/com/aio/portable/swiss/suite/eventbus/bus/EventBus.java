package com.aio.portable.swiss.suite.eventbus.bus;


import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.namespace.EventNamespace;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.exception.NotExistEventNamespaceException;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
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
        EventNamespace eventGroup = buildEventGroup(group);
        addIfNotExists(eventGroup);
    }

    @Override
    public void add(EventNamespace eventGroup) {
        addIfNotExists(eventGroup);
    }

    private void set(EventNamespace eventGroup) {
        String group = eventGroup.getNamespace();
        if (StringUtils.isEmpty(group)) {
            throw new IllegalArgumentException("group is empty.");
        }
        if (exists(group)) {
            persist(eventGroup);
            String[] tables = spellTables();
            persistentContainer.setTable(group, eventGroup, tables);
        } else {
            throw new NotExistEventNamespaceException(MessageFormat.format("group {0} is not exist.", eventGroup.getNamespace()));
        }
    }

    private EventNamespace addIfNotExists(EventNamespace eventGroup) {
        String group = eventGroup.getNamespace();
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
        EventNamespace eventGroup = get(group);
        eventGroup.clear();

        persistentContainer.removeTable(group, tables);
    }

    @Override
    public void clear() {
        String[] tables = spellTables();
        collection().entrySet().forEach(c -> {
            c.getValue().clear();
            remove(c.getValue().getNamespace());
        });

        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public boolean exists(String group) {
        String[] tables = spellTables();
        return persistentContainer.existsTable(group, tables);
    }

    @Override
    public EventNamespace get(String group) {
        String[] tables = spellTables();
        EventNamespace eventGroup = persistentContainer.getTable(group, EventNamespace.class, tables);
        persist(eventGroup);
        return eventGroup;
    }

    public void enable(String group, boolean enabled) {
        EventNamespace eventGroup = get(group);
        eventGroup.setEnabled(enabled);
        set(eventGroup);
    }

    @Override
    public Map<String, EventNamespace> collection() {
        String[] tables = spellTables();
        return persistentContainer.getAllTable(EMPTY, EventNamespace.class, tables)
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
            } else if (!CollectionSugar.isEmpty(event.getGroups()) && event.getGroups().contains(eventGroup.getNamespace())) {
                map.putAll(eventGroup.send(event));
            }
        });

        return map;
    }


    public EventNamespace buildEventGroup(String group) {
        EventNamespace eventGroup = new EventNamespace(this.nodePersistence, group);
        return eventGroup;
    }

    public EventSubscriber buildEventSubscriber(String group, String subscriber, List<String> tags) {
        EventNamespace eventGroup = buildEventGroup(group);
        EventSubscriber eventSubscriber = eventGroup.buildEventSubscriber(subscriber, tags);
        return eventSubscriber;
    }

    public EventNamespace persist(EventNamespace eventGroup) {
        eventGroup.setNodePersistence(this.nodePersistence);
        return eventGroup;
    }

    public EventSubscriber addEventSubscriber(String group, String subscriber, List<String> tags) {
        EventNamespace eventGroup = buildEventGroup(group);
        EventSubscriber eventSubscriber = eventGroup.buildEventSubscriber(subscriber, tags);
        add(eventGroup);
        eventGroup.add(eventSubscriber);
        return eventSubscriber;
    }

    public EventSubscriber persist(EventSubscriber eventSubscriber) {
        eventSubscriber.setNodePersistence(this.nodePersistence);
        return eventSubscriber;
    }







}
