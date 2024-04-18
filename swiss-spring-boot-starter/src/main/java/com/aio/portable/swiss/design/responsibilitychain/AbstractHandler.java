package com.aio.portable.swiss.design.responsibilitychain;

public abstract class AbstractHandler {
    AbstractHandler next;

    public AbstractHandler getNext() {
        return next;
    }

    public AbstractHandler(AbstractHandler next) {
        this.next = next;
    }

    public boolean hasNext() {
        return next != null;
    }

    public abstract Object handle(Responsibility responsibility);

    public void handleAndWriteBack(Responsibility responsibility) {
        Object result = handle(responsibility);
        responsibility.complete(result);
    }
}
