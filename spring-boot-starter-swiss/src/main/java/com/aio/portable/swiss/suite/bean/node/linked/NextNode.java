package com.aio.portable.swiss.suite.bean.node.linked;

import java.util.Iterator;

interface NextNode<T> extends Iterator<T> {
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
