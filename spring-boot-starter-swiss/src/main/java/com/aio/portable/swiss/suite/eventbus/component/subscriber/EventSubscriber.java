package com.aio.portable.swiss.suite.eventbus.component.subscriber;

import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.action.EventAction;
import com.aio.portable.swiss.suite.eventbus.component.action.RestTemplateEventAction;
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
    public void add(EventAction eventAction) {
        String action = eventAction.getAction();
        String[] tables = spellTables();
//        String table = mutateTable();
        persistentContainer.set(action, eventAction, tables);
    }

    public void set(EventAction eventAction) {
        String action = eventAction.getAction();
        if (StringUtils.isEmpty(action)) {
            throw new IllegalArgumentException("action is empty.");
        }
        if (exists(action)) {
//            persist(eventNamespace);
            String[] tables = spellTables();
            persistentContainer.set(action, eventAction, tables);
        } else {
            throw new NotExistEventNamespaceException(MessageFormat.format("action {0} is not exist.", eventAction.getAction()));
        }
    }

    @Override
    public void remove(EventAction eventAction) {
        String[] tables = spellTables();
//        String table = mutateTable();
        String key = eventAction.getAction();
        persistentContainer.remove(key, tables);
    }

    @Override
    public void remove(String action) {
        String[] tables = spellTables();
//        String table = mutateTable();
        String key = action;
        persistentContainer.remove(key, tables);
    }

    @Override
    public void clear() {
        String[] tables = spellTables();
//        String table = mutateTable();
        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public EventAction get(String action) {
        String[] tables = spellTables();
//        String table = mutateTable();
        return persistentContainer.get(action, EventAction.class, tables);
    }

    public void enable(String action, boolean enabled) {
        EventAction eventAction = get(action);
        eventAction.setEnabled(enabled);
        set(eventAction);
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
    public Map<String, EventAction> collection() {
        String[] tables = spellTables();
//        String table = mutateTable();
        return persistentContainer.getAll(EMPTY, EventAction.class, tables);
    }

    @Override
    public <E extends Event> Map<String, EventAction> onReceive(E event) {
        return dispatch(event);
    }

    private <E extends Event> Map<String, EventAction> dispatch(E event) {
        if (!exists())
            return null;
        if (!isEnabled())
            return null;

        Stream<EventAction> stream = this.collection()
                .values()
                .stream()
                .filter(c -> c.isEnabled());
        stream = concurrent ? stream.parallel() : stream;
        stream.forEach(action -> {
            if (action instanceof RestTemplateEventAction) {
                Object push = action.push(event);
            } else {
                Object push = action.push(event);
            }
        });

        return this.collection();
    }



}
