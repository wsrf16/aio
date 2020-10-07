package com.aio.portable.swiss.suite.eventbus.component.subscriber;

import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.handler.EventHandler;
import com.aio.portable.swiss.suite.eventbus.component.handler.RestTemplateEventHandler;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.exception.NotExistEventNamespaceException;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class EventSubscriber extends AbstractEventSubscriber {
    private final static String EMPTY = "";
    @JsonIgnore
    protected boolean concurrent = true;

    @JsonIgnore
    NodePersistence nodePersistence;

    @JsonIgnore
    private PersistentContainer persistentContainer;

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public NodePersistence getNodePersistence() {
        return nodePersistence;
    }

    public void setNodePersistence(NodePersistence nodePersistence) {
        this.nodePersistence = nodePersistence;
        this.persistentContainer = PersistentContainer.buildEventSubscriberPersistentContainer(nodePersistence);
    }

    public EventSubscriber() {
    }

    private String mutateTable() {
        return getSubscriber();
    }

    private String[] spellTables() {
        return new String[]{EventBusConfig.EVENT_BUS_TABLE, getNamespace(), getSubscriber()};
    }

    public EventSubscriber(@NotNull NodePersistence nodePersistence, @NotNull String namespace, @NotNull List<String> topics, @NotNull String name) {
        super(namespace, topics, name);
        setNodePersistence(nodePersistence);
    }


    @Override
    public void add(EventHandler eventHandler) {
        String handler = eventHandler.getHandler();
        String[] tables = spellTables();
//        String table = mutateTable();
        persistentContainer.set(handler, eventHandler, tables);
    }

    public void set(EventHandler eventHandler) {
        String handler = eventHandler.getHandler();
        if (StringUtils.isEmpty(handler)) {
            throw new IllegalArgumentException("handler is empty.");
        }
        if (exists(handler)) {
//            persist(eventNamespace);
            String[] tables = spellTables();
            persistentContainer.set(handler, eventHandler, tables);
        } else {
            throw new NotExistEventNamespaceException(MessageFormat.format("handler {0} is not exist.", eventHandler.getHandler()));
        }
    }

    @Override
    public void remove(EventHandler eventHandler) {
        String[] tables = spellTables();
//        String table = mutateTable();
        String key = eventHandler.getHandler();
        persistentContainer.remove(key, tables);
    }

    @Override
    public void remove(String handler) {
        String[] tables = spellTables();
//        String table = mutateTable();
        String key = handler;
        persistentContainer.remove(key, tables);
    }

    @Override
    public void clear() {
        String[] tables = spellTables();
//        String table = mutateTable();
        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public EventHandler get(String handler) {
        String[] tables = spellTables();
//        String table = mutateTable();
        return persistentContainer.get(handler, EventHandler.class, tables);
    }

    public void enable(String handler, boolean enabled) {
        EventHandler eventHandler = get(handler);
        eventHandler.setEnabled(enabled);
        set(eventHandler);
    }

    @Override
    public boolean exists() {
        String table = mutateTable();
        return persistentContainer.existsTable(table);
    }

    @Override
    public boolean exists(String name) {
        String[] tables = spellTables();
//        String table = mutateTable();
        return persistentContainer.exists(name, tables);
    }

    @Override
    public Map<String, EventHandler> collection() {
        String[] tables = spellTables();
//        String table = mutateTable();
        return persistentContainer.getAll(EMPTY, EventHandler.class, tables);
    }

    @Override
    public <E extends Event> Map<String, EventHandler> onReceive(E event) {
        return dispatch(event);
    }

    private <E extends Event> Map<String, EventHandler> dispatch(E event) {
        if (!exists())
            return null;
        if (!isEnabled())
            return null;

        Stream<EventHandler> stream = this.collection()
                .values()
                .stream()
                .filter(c -> c.isEnabled());
        stream = concurrent ? stream.parallel() : stream;
        stream.forEach(handler -> {
            if (handler instanceof RestTemplateEventHandler) {
                Object push = handler.push(event);
            } else {
                Object push = handler.push(event);
            }
        });

        return this.collection();
    }



}
