package com.york.portable.swiss.bean.node;

import com.york.portable.swiss.bean.node.next.layered.LayeredNextNode;

import java.util.Iterator;

public interface NextNode<T> extends Iterator<T> {
    default T next() {
        return getNext();
    }

    T getNext();

    void setNext(T next);

    default boolean tail() {
        return getNext() == null;
    }

    default boolean hasNext() {
        return getNext() != null;
    }
}
