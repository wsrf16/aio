package com.york.portable.swiss.bean.node;

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