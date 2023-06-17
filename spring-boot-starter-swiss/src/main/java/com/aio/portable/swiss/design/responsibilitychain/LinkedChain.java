package com.aio.portable.swiss.design.responsibilitychain;

public class LinkedChain {
    boolean isRunning = false;

    public static final LinkedChain newInstance(AbstractHandler handler) {
        return new LinkedChain(handler);
    }

    private AbstractHandler handler;

    private LinkedChain(AbstractHandler handler) {
        this.handler = handler;
    }

    public synchronized void handle(Responsibility responsibility) {
        isRunning = true;
        handler.handleAndWriteBack(responsibility);
        while (handler.hasNext()) {
            handler = handler.getNext();
            responsibility.next();
            handler.handleAndWriteBack(responsibility);
        }
        isRunning = false;
    }

}
