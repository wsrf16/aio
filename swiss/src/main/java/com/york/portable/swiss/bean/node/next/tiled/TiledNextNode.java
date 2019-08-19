package com.york.portable.swiss.bean.node.next.tiled;

import com.york.portable.swiss.resource.ClassUtils;
import com.york.portable.swiss.sugar.PropertyExtra;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public interface TiledNextNode {
    String[] IGNORE_PROPERTIES = {"prev", "next"};

    static <T extends TiledNextNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassUtils.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    TiledNextNode getNext();

    void setNext(TiledNextNode next);

    TiledNextNode getPrev();

    void setPrev(TiledNextNode prev);

    default void isolate() {
        setPrev(null);
        setNext(null);
    }

    default boolean head() {
        return getPrev() == null;
    }

    default boolean tail() {
        return getNext() == null;
    }

}
