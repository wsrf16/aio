package com.aio.portable.swiss.suite.bean.node.relation;

public class IDLinkedNode<T, ID> implements IDNode<T, ID> {
    T value;
    ID id;
    ID nextId;

    public IDLinkedNode(T value, ID id, ID nextId) {
        setValue(value);
        setId(id);
        setNextId(nextId);
    }

    public IDLinkedNode(T value, ID id) {
        setValue(value);
        setId(id);
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public ID getNextId() {
        return nextId;
    }

    @Override
    public void setNextId(ID nextId) {
        this.nextId = nextId;
    }
}





