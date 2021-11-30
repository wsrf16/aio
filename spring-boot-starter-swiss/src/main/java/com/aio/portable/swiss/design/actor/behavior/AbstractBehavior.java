package com.aio.portable.swiss.design.actor.behavior;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractBehavior<INPUT, V> implements Callable<V> {
    private INPUT input;

    private ExecutorService service;

    public AbstractBehavior(ExecutorService service, INPUT input) {
        this.service = service;
        this.input = input;
    }

    public AbstractBehavior(ExecutorService service) {
        this.service = service;
    }

    public V submit() throws ExecutionException, InterruptedException {
        Future<V> future = service.submit(this);
        V result = future.get();
        return result;
    }
}
