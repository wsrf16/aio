package com.aio.portable.swiss.suite.eventbus.component.subscriber;

import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.handler.EventHandler;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
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

    private String[] tables() {
        return new String[]{EventBusConfig.EVENT_BUS_TABLE, getGroup(), getSubscriber()};
    }

    public EventSubscriber(@NotNull NodePersistence nodePersistence, @NotNull String group, @NotNull String name) {
        super(group, name);
        setNodePersistence(nodePersistence);
    }


    @Override
    public void add(EventHandler handler) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = handler.getName();
        persistentContainer.set(key, handler, tables);
    }

    @Override
    public void remove(EventHandler handler) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = handler.getName();
        persistentContainer.remove(key, tables);
    }

    @Override
    public void remove(String name) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = name;
        persistentContainer.remove(key, tables);
    }

    @Override
    public void clear() {
        String[] tables = tables();
//        String table = mutateTable();
        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public EventHandler get(String name) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = name;
        return persistentContainer.get(key, EventHandler.class, tables);
    }

    @Override
    public boolean exists() {
        String table = mutateTable();
        return persistentContainer.existsTable(table);
    }

    @Override
    public boolean exists(String name) {
        String[] tables = tables();
//        String table = mutateTable();
        return persistentContainer.exists(name, tables);
    }

    @Override
    public Map<String, EventHandler> collection() {
        String[] tables = tables();
//        String table = mutateTable();
        return persistentContainer.getAll(EMPTY, EventHandler.class, tables);
    }

    @Override
    public <E extends Event> void onReceiveEvent(E event) {
        dispatch(event);
    }

    private <E extends Event> void dispatch(E event) {
        if (!exists())
            return;
        if (!isEnabled())
            return;

        Stream<Map.Entry<String, EventHandler>> stream = concurrent ?
                this.collection().entrySet().parallelStream()
                : this.collection().entrySet().stream();
        stream.forEach(c -> {
            EventHandler handler = c.getValue();
            Object push = handler.push(event);

//            if (CollectionSugar.isEmpty(subscriber.getTags())
//                    || event.getTags().containsAll(subscriber.getTags())) {
//                if (subscriber instanceof RestTemplateHandler) {
//                    Object push = subscriber.push(event);
//                } else {
//                    Object push = subscriber.push(event);
//                }
//            }
        });
    }



}
