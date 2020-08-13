package com.aio.portable.swiss.suite.eventbus.listener;

import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.eventbus.subscriber.RestTemplateSubscriber;
import com.aio.portable.swiss.suite.eventbus.subscriber.Subscriber;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Stream;

public class EventListener extends AbstractEventListener {
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
        this.persistentContainer = PersistentContainer.buildEventListenerPersistentContainer(nodePersistence);
    }

    public EventListener() {
    }

    private String mutateTable() {
        return getListener();
    }

    private String[] tables() {
        return new String[]{EventBusConfig.EVENT_BUS_TABLE, getGroup(), getListener()};
    }

    public EventListener(@NotNull NodePersistence nodePersistence, @NotNull String group, @NotNull String name) {
        super(group, name);
        setNodePersistence(nodePersistence);
    }


    @Override
    public void add(Subscriber subscriber) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = subscriber.getName();
        persistentContainer.set(key, subscriber, tables);
    }

    @Override
    public void remove(Subscriber subscriber) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = subscriber.getName();
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
    public Subscriber get(String name) {
        String[] tables = tables();
//        String table = mutateTable();
        String key = name;
        return persistentContainer.get(key, Subscriber.class, tables);
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
    public Map<String, Subscriber> collection() {
        String[] tables = tables();
//        String table = mutateTable();
        return persistentContainer.getAll(EMPTY, Subscriber.class, tables);
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

        Stream<Map.Entry<String, Subscriber>> stream = concurrent ?
                this.collection().entrySet().parallelStream()
                : this.collection().entrySet().stream();
        stream.forEach(c -> {
            Subscriber subscriber = c.getValue();

            if (CollectionSugar.isEmpty(subscriber.getTags())
                    || subscriber.getTags().containsAll(event.getTags())) {
                if (subscriber instanceof RestTemplateSubscriber) {
                    Object push = subscriber.push(event);
                } else {
                    Object push = subscriber.push(event);
                }
            }
        });
    }



}
