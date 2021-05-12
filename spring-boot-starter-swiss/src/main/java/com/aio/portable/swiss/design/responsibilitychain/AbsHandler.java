package com.aio.portable.swiss.design.responsibilitychain;

public abstract class AbsHandler {
    AbsHandler next;

    public AbsHandler getNext() {
        return next;
    }

    public AbsHandler(AbsHandler next) {
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
