package com.aio.portable.swiss.suite.eventbus.group;

import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Collectors;

public class EventGroup extends AbstractEventGroup {
    private final static String EMPTY = "";
    @JsonIgnore
    NodePersistence nodePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public NodePersistence getNodePersistence() {
        return nodePersistence;
    }

    public void setNodePersistence(NodePersistence nodePersistence) {
        this.nodePersistence = nodePersistence;
        this.persistentContainer = PersistentContainer.buildEventGroupPersistentContainer(nodePersistence);
    }

    public PersistentContainer getPersistentContainer() {
        return persistentContainer;
    }

    public EventGroup() {
    }

    public EventGroup(@NotNull NodePersistence nodePersistence, @NotNull String group) {
        super(group);
        setNodePersistence(nodePersistence);
    }

    private String[] spellTables() {
        return new String[]{EventBusConfig.EVENT_BUS_TABLE, getGroup()};
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
        if (StringUtils.isEmpty(this.getGroup())) {
            throw new IllegalArgumentException("eventGroup.group is null.");
        }
        String[] tables = spellTables();
        String key = eventListener.getListener();
        persist(eventListener);
        persistentContainer.setTable(key, eventListener, tables);
    }

    public EventListener addIfNotExists(EventListener eventListener) {
        set(eventListener);
        return eventListener;
    }

    @Override
    public void remove(String listener) {
        EventListener eventListener = get(listener);
        eventListener.clear();

        String[] tables = spellTables();
        String key = listener;
        persistentContainer.removeTable(key, tables);
    }

    @Override
    public void remove(EventListener eventListener) {
        persist(eventListener);
        eventListener.clear();

        String[] tables = spellTables();
        String key = eventListener.getListener();
        persistentContainer.removeTable(key, tables);
    }

    @Override
    public void clear() {
        collection().entrySet().forEach(c -> {
            c.getValue().clear();
            remove(c.getValue().getListener());
        });

        String[] tables = spellTables();
        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public EventListener get(String listener) {
        String[] tables = spellTables();
        String key = listener;
        EventListener eventListener = persistentContainer.getTable(key, EventListener.class, tables);
        persist(eventListener);
        return eventListener;
    }

    @Override
    public boolean exists(String listener) {
        String[] tables = spellTables();
        String key = listener;
        return persistentContainer.existsTable(key, tables);
    }

    @Override
    public boolean exists() {
        String[] tables = spellTables();
        return persistentContainer.existsTable("", tables);
    }

    @Override
    public Map<String, EventListener> collection() {
        String[] tables = spellTables();
        return persistentContainer.getAllTable(EMPTY, EventListener.class, tables)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

    public EventListener buildEventListener(String listener) {
        EventListener eventListener = new EventListener(this.nodePersistence, getGroup(), listener);
        return eventListener;
    }

    public EventListener persist(EventListener eventListener) {
        eventListener.setNodePersistence(this.nodePersistence);
        return eventListener;
    }

    public final <E extends Event> void send(E event) {
        this.collection().values().stream().parallel().forEach(eventListener -> {
            eventListener.onReceiveEvent(event);
        });
    }


}
