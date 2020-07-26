package com.aio.portable.swiss.suite.eventbus.subscriber;

import com.aio.portable.swiss.suite.eventbus.event.AbstractEvent;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleSubscriber extends Subscriber {

    protected Function<AbstractEvent, Object> func;

    protected Consumer<Object> succeedBack;

    protected Consumer<Exception> failedBack;

//    public Function<Event, Object> getFunc() {
//        return func;
//    }
//
//    public void setFunc(Function<Event, Object> func) {
//        this.func = func;
//    }
//
//    public Consumer<Object> getSucceedBack() {
//        return succeedBack;
//    }
//
//    public void setSucceedBack(Consumer<Object> succeedBack) {
//        this.succeedBack = succeedBack;
//    }
//
//    public Consumer<Exception> getFailedBack() {
//        return failedBack;
//    }
//
//    public void setFailedBack(Consumer<Exception> failedBack) {
//        this.failedBack = failedBack;
//    }


    public SimpleSubscriber() {}

    public SimpleSubscriber(@NotNull String name, @NotNull List<String> tags) {
        super(name, tags);
    }

    @Override
    public <E extends AbstractEvent> Object push(E event) {
        Object responseEntity = proxy(this.func, event);
        return responseEntity;
    }

    public <E extends AbstractEvent> Object proxy(Function<E, Object> func, E event) {
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
