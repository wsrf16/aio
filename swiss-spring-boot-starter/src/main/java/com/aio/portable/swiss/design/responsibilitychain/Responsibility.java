package com.aio.portable.swiss.design.responsibilitychain;

import java.util.*;

public class Responsibility {
    public Integer step;

    public Map.Entry<Integer, Object> current;

    private TreeMap<Integer, Object> map = new TreeMap<>();

    public Iterator<Map.Entry<Integer, Object>> iterator;

    public Responsibility(List<Integer> steps) {
        steps.forEach(c -> map.put(c, null));
        iterator = map.entrySet().iterator();
        this.current = iterator.next();
        this.step = steps.get(0);
    }

    public boolean isComplete() {
        return map.containsKey(step);
    }

    public void complete(Object result) {
        map.put(step, result);
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public void next() {
        this.current = iterator.next();
    }

    public Map.Entry<Integer, Object> getCurrent() {
        return this.current;
    }
}
