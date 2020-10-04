package com.aio.portable.swiss.suite.eventbus.bus;


import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.namespace.EventNamespace;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.exception.NotExistEventNamespaceException;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
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


    public void add(String namespace) {
        EventNamespace eventNamespace = buildEventNamespace(namespace);
        addIfNotExists(eventNamespace);
    }

    @Override
    public void add(EventNamespace eventNamespace) {
        addIfNotExists(eventNamespace);
    }

    private void set(EventNamespace eventNamespace) {
        String namespace = eventNamespace.getNamespace();
        if (StringUtils.isEmpty(namespace)) {
            throw new IllegalArgumentException("namespace is empty.");
        }
        if (exists(namespace)) {
            persist(eventNamespace);
            String[] tables = spellTables();
            persistentContainer.setTable(namespace, eventNamespace, tables);
        } else {
            throw new NotExistEventNamespaceException(MessageFormat.format("namespace {0} is not exist.", eventNamespace.getNamespace()));
        }
    }

    private EventNamespace addIfNotExists(EventNamespace eventNamespace) {
        String namespace = eventNamespace.getNamespace();
        if (exists(namespace)) {
            eventNamespace = get(namespace);
        } else {
            eventNamespace = persist(eventNamespace);
            String[] tables = spellTables();
            if (!exists(tables[0]))
                persistentContainer.setTable(tables[0], this);
            persistentContainer.setTable(namespace, eventNamespace, tables);
        }
        return eventNamespace;
    }

    @Override
    public void remove(String namespace) {
        String[] tables = spellTables();
        EventNamespace eventNamespace = get(namespace);
        eventNamespace.clear();

        persistentContainer.removeTable(namespace, tables);
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
    public boolean exists(String namespace) {
        String[] tables = spellTables();
        return persistentContainer.existsTable(namespace, tables);
    }

    @Override
    public EventNamespace get(String namespace) {
        String[] tables = spellTables();
        EventNamespace eventNamespace = persistentContainer.getTable(namespace, EventNamespace.class, tables);
        persist(eventNamespace);
        return eventNamespace;
    }

    public void enable(String namespace, boolean enabled) {
        EventNamespace eventNamespace = get(namespace);
        eventNamespace.setEnabled(enabled);
        set(eventNamespace);
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
                .filter(eventNamespace -> eventNamespace.exists() && eventNamespace.isEnabled())
                .forEach(eventNamespace -> {
            if (CollectionSugar.isEmpty(event.getNamespaces())) {
                map.putAll(eventNamespace.send(event));
            } else if (!CollectionSugar.isEmpty(event.getNamespaces()) && event.getNamespaces().contains(eventNamespace.getNamespace())) {
                map.putAll(eventNamespace.send(event));
            }
        });

        return map;
    }


    public EventNamespace buildEventNamespace(String namespace) {
        EventNamespace eventNamespace = new EventNamespace(this.nodePersistence, namespace);
        return eventNamespace;
    }

    public EventSubscriber buildEventSubscriber(String namespace, String subscriber, List<String> topics) {
        EventNamespace eventNamespace = buildEventNamespace(namespace);
        EventSubscriber eventSubscriber = eventNamespace.buildEventSubscriber(subscriber, topics);
        return eventSubscriber;
    }

    public EventNamespace persist(EventNamespace eventNamespace) {
        eventNamespace.setNodePersistence(this.nodePersistence);
        return eventNamespace;
    }

    public EventSubscriber addEventSubscriber(String namespace, String subscriber, List<String> topics) {
        EventNamespace eventNamespace = buildEventNamespace(namespace);
        EventSubscriber eventSubscriber = eventNamespace.buildEventSubscriber(subscriber, topics);
        add(eventNamespace);
        eventNamespace.add(eventSubscriber);
        return eventSubscriber;
    }

    public EventSubscriber persist(EventSubscriber eventSubscriber) {
        eventSubscriber.setNodePersistence(this.nodePersistence);
        return eventSubscriber;
    }







}
