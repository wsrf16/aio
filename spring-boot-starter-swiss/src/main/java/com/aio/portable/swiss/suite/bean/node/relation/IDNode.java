package com.aio.portable.swiss.suite.bean.node.relation;

interface IDNode<T, ID> {
    T getValue();

    void setValue(T value);

    ID getId();

    void setId(ID id);

    ID getNextId();

    void setNextId(ID nextId);

    default boolean tail() {
        return getNextId() == null;
    }

    default boolean hasNext() {
        return getNextId() != null;
    }

}
