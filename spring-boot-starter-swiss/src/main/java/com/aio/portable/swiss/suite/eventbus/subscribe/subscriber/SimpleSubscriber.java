package com.aio.portable.swiss.suite.eventbus.subscribe.subscriber;

import com.aio.portable.swiss.suite.eventbus.event.Event;

import javax.validation.constraints.NotNull;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleSubscriber extends Subscriber {

    Function<Event, Object> func;

    Consumer<Object> succeedBack;

    Consumer<Exception> failedBack;

    public Function<Event, Object> getFunc() {
        return func;
    }

    public void setFunc(Function<Event, Object> func) {
        this.func = func;
    }

    public Consumer<Object> getSucceedBack() {
        return succeedBack;
    }

    public void setSucceedBack(Consumer<Object> succeedBack) {
        this.succeedBack = succeedBack;
    }

    public Consumer<Exception> getFailedBack() {
        return failedBack;
    }

    public void setFailedBack(Consumer<Exception> failedBack) {
        this.failedBack = failedBack;
    }

    public SimpleSubscriber(@NotNull String name, @NotNull String topic) {
        super(name, topic);
    }

    @Override
    public <E extends Event> Object push(E event) {
        Object responseEntity = proxy(this.func, event);
        return responseEntity;
    }

    public <E extends Event> Object proxy(Function<E, Object> func, E event) {
        Object responseEntity = null;
        try {
            responseEntity = func.apply(event);
        } catch (Exception e) {
            e.printStackTrace();
            if (failedBack != null)
                failedBack.accept(e);
        }
        if (succeedBack != null)
            succeedBack.accept(responseEntity);
        return responseEntity;
    }




}
