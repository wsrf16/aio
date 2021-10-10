package com.aio.portable.swiss.suite.bean.node.relation;

public class RelationLinkedNode<T, ID> implements RelationNode<T, ID> {
    T value;
    ID id;
    ID nextId;

    public RelationLinkedNode(T value, ID id, ID nextId) {
        setValue(value);
        setId(id);
        setNextId(nextId);
    }

    public RelationLinkedNode(T value, ID id) {
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





