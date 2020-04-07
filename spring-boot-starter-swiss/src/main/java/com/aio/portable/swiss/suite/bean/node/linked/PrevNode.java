package com.aio.portable.swiss.suite.bean.node.linked;

public interface PrevNode<T> {
    default T prev() {
        return getPrev();
    }

    T getPrev();

    void setPrev(T prev);

    default boolean head() {
        return getPrev() == null;
    }

    default boolean hasPrev() {
        return getPrev() != null;
    }
}
