package com.aio.portable.swiss.suite.eventbus.component.group;

import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;
import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.exception.NotExistEventGroupException;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void add(String subscriber) {
        EventSubscriber eventSubscriber = buildEventSubscriber(subscriber);
        add(eventSubscriber);
    }

    @Override
    public void add(EventSubscriber eventSubscriber) {
        addIfNotExists(eventSubscriber);
    }

    private void set(EventSubscriber eventSubscriber) {
        String subscriber = eventSubscriber.getSubscriber();
        if (StringUtils.isEmpty(subscriber)) {
            throw new IllegalArgumentException("subscriber is empty.");
        }
        if (exists(subscriber)) {
            persist(eventSubscriber);
            String[] tables = spellTables();
            persistentContainer.setTable(subscriber, eventSubscriber, tables);
        } else {
            throw new NotExistEventGroupException(MessageFormat.format("subscriber {0} is not exist.", subscriber));
        }

    }

    public EventSubscriber addIfNotExists(EventSubscriber eventSubscriber) {
        String subscriber = eventSubscriber.getSubscriber();
        if (exists(subscriber))
            eventSubscriber = get(subscriber);
        else {
            persist(eventSubscriber);
            String[] tables = spellTables();
            if (!exists(tables[0]))
                persistentContainer.setTable(tables[0], this);
            persistentContainer.setTable(subscriber, eventSubscriber, tables);
        }
        return eventSubscriber;
    }

    @Override
    public void remove(String subscriber) {
        EventSubscriber eventSubscriber = get(subscriber);
        eventSubscriber.clear();

        String[] tables = spellTables();
        String key = subscriber;
        persistentContainer.removeTable(key, tables);
    }

    @Override
    public void remove(EventSubscriber eventSubscriber) {
        persist(eventSubscriber);
        eventSubscriber.clear();

        String[] tables = spellTables();
        String key = eventSubscriber.getSubscriber();
        persistentContainer.removeTable(key, tables);
    }

    @Override
    public void clear() {
        collection().entrySet().forEach(c -> {
            c.getValue().clear();
            remove(c.getValue().getSubscriber());
        });

        String[] tables = spellTables();
        persistentContainer.clearTable(EMPTY, tables);
    }

    @Override
    public EventSubscriber get(String subscriber) {
        String[] tables = spellTables();
        String key = subscriber;
        EventSubscriber eventSubscriber = persistentContainer.getTable(key, EventSubscriber.class, tables);
        persist(eventSubscriber);
        return eventSubscriber;
    }

    public void enable(String subscriber, boolean enabled) {
        EventSubscriber eventSubscriber = get(subscriber);
        eventSubscriber.setEnabled(enabled);
        set(eventSubscriber);
    }

    @Override
    public boolean exists(String subscriber) {
        String[] tables = spellTables();
        String key = subscriber;
        return persistentContainer.existsTable(key, tables);
    }

    @Override
    public boolean exists() {
        String[] tables = spellTables();
        return persistentContainer.existsTable("", tables);
    }

    @Override
    public Map<String, EventSubscriber> collection() {
        String[] tables = spellTables();
        return persistentContainer.getAllTable(EMPTY, EventSubscriber.class, tables)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> persist(e.getValue())));
    }

    public EventSubscriber buildEventSubscriber(String subscriber) {
        EventSubscriber eventSubscriber = new EventSubscriber(this.nodePersistence, getGroup(), subscriber);
        return eventSubscriber;
    }

    public EventSubscriber persist(EventSubscriber eventSubscriber) {
        eventSubscriber.setNodePersistence(this.nodePersistence);
        return eventSubscriber;
    }

    public final <E extends Event> Map<String, EventSubscriber> send(E event) {
        Map<String, EventSubscriber> map = new HashMap<>();

        Stream<EventSubscriber> stream = this.collection()
                .values()
                .stream()
                .filter(c -> c.exists() && c.isEnabled())
                .parallel();

        stream.forEach(subscriber -> {
            if (event.getTags().containsAll(subscriber.getTags())) {
                subscriber.onReceive(event);
                map.put(subscriber.getGroup(), subscriber);
            }
//            if (CollectionSugar.isEmpty(event.getTags())) {
//                subscriber.onReceive(event);
//            } else if (event.getTags().containsAll(subscriber.getTags())) {
//                subscriber.onReceive(event);
//            }
        });
        return map;
    }


}
