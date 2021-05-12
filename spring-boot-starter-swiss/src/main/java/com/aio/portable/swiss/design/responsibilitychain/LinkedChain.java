package com.aio.portable.swiss.design.responsibilitychain;

public class LinkedChain {
    boolean isRunning = false;

    public static LinkedChain newInstance(AbsHandler handler) {
        return new LinkedChain(handler);
    }

    private AbsHandler handler;

    private LinkedChain(AbsHandler handler) {
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
