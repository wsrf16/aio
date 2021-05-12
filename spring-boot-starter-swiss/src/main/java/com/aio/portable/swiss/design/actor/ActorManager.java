package com.aio.portable.swiss.design.actor;

import java.util.*;

public class ActorManager {
    private LinkedList<Actor> actorQueue = new LinkedList<>();

    public LinkedList<Actor> getActorQueue() {
        return actorQueue;
    }

    public ActorManager start() {
        actorQueue.stream()
                .filter(c -> c.getActorStatus() == ActorStatus.INIT)
                .forEach(c -> new Thread(c).start());
        return this;
    }

    public ActorManager detach(String name) {
        actorQueue.removeIf(c -> Objects.equals(c.getName(), name));
        return this;
    }

    public ActorManager tryStop(String name) {
        actorQueue.stream()
                .filter(c -> c.getActorStatus() == ActorStatus.RUNNING)
                .forEach(c -> {
                    if (Objects.equals(c.getName(), name))
                        c.tryStop();
                });
        return this;
    }

    public <T, R> ActorManager add(Actor<T, R>... actors) {
        Arrays.stream(actors).forEach(actor -> actorQueue.add(actor));
        return this;
    }

    public <T, R> ActorManager add(Collection<Actor<T, R>> actors) {
        actors.stream().forEach(actor -> actorQueue.add(actor));
        return this;
    }

    public <T, R> ActorManager addThenStart(Actor<T, R>... actors) {
        Arrays.stream(actors).forEach(actor -> {
            actorQueue.add(actor);
            new Thread(actor).start();
        });
        return this;
    }

    public <T, R> ActorManager addThenStart(Collection<Actor<T, R>> actors) {
        actors.stream().forEach(actor -> {
            actorQueue.add(actor);
            new Thread(actor).start();
        });
        return this;
    }



}
