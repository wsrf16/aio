package com.aio.portable.swiss.design.responsibilitychain;

import java.util.Comparator;
import java.util.TreeMap;

public class MapChain {
    boolean isRunning = false;

    public static MapChain newInstance(TreeMap<Integer, AbstractHandler> handlerMaps) {
        return new MapChain(handlerMaps);
    }

    private TreeMap<Integer, AbstractHandler> handlerMaps;

    private MapChain(TreeMap<Integer, AbstractHandler> handlerMaps) {
        this.handlerMaps = handlerMaps;
    }

    public synchronized void handle(Responsibility responsibility) {
        isRunning = true;
//        List<Map.Entry<Integer, AbsHandler>> handlerSortList = handlerMaps.entrySet().stream().sorted(Comparator.comparing(c -> c.getKey())).collect(Collectors.toList());
//        for (Map.Entry<Integer, AbsHandler> entry : handlerSortList) {
//            Object result = entry.getValue().handle(responsibility);
//            responsibility.complete(result);
//            if (responsibility.hasNext())
//                responsibility.next();
//        }
        handlerMaps.entrySet().stream().sorted(Comparator.comparing(c -> c.getKey()))
                .forEach(entry -> {
                    Object result = entry.getValue().handle(responsibility);
                    responsibility.complete(result);
                    if (responsibility.hasNext())
                        responsibility.next();
                });
        isRunning = false;
    }


}
