package com.york.portable.swiss.bean.node.next.tiled;

import com.york.portable.swiss.resource.ClassUtils;
import org.springframework.beans.BeanUtils;

public interface TiledNextNode {
    static <T extends TiledNextNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassUtils.newInstance(clazz);
        BeanUtils.copyProperties(item, t);
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
