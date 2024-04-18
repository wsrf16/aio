package com.aio.portable.swiss.design.actor.behavior;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractBehavior<IN, OUT> implements Callable<OUT> {
    private IN input;

    private ExecutorService service;

    public AbstractBehavior(IN input, ExecutorService service) {
        this.input = input;
        this.service = service;
    }

    public AbstractBehavior(ExecutorService service) {
        this.service = service;
    }

    public OUT submit() throws ExecutionException, InterruptedException {
        Future<OUT> future = service.submit(this);
        OUT result = future.get();
        return result;
    }
}
