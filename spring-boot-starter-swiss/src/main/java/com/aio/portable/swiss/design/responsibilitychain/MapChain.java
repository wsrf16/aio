package com.aio.portable.swiss.design.responsibilitychain;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MapChain {
    boolean isRunning = false;

    public static MapChain newInstance(TreeMap<Integer, AbsHandler> handlerMaps) {
        return new MapChain(handlerMaps);
    }

    private TreeMap<Integer, AbsHandler> handlerMaps;

    private MapChain(TreeMap<Integer, AbsHandler> handlerMaps) {
        this.handlerMaps = handlerMaps;
    }

    public synchronized void handle(Responsibility responsibility) {
        isRunning = true;
        List<Map.Entry<Integer, AbsHandler>> handlerSortList = handlerMaps.entrySet().stream().sorted(Comparator.comparing(c -> c.getKey())).collect(Collectors.toList());
        for (Map.Entry<Integer, AbsHandler> entry : handlerSortList) {
            Object result = entry.getValue().handle(responsibility);
            responsibility.complete(result);
            if (responsibility.hasNext())
                responsibility.next();
        }
        isRunning = false;
    }


}
