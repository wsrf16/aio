package com.york.portable.swiss.bean.node.relation;

public class RelationNodeBean<ID, T> implements RelationNode<ID, T> {
    T item;
    ID nodeId;
    ID nextId;

    public RelationNodeBean(T item, ID nodeId, ID nextId) {
        setItem(item);
        setNodeId(nodeId);
        setNextId(nextId);
    }

    public RelationNodeBean(T item, ID nodeId) {
        setItem(item);
        setNodeId(nodeId);
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public ID getNodeId() {
        return nodeId;
    }

    @Override
    public void setNodeId(ID nodeId) {
        this.nodeId = nodeId;
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

//interface Role {
//    <T> T getLinkedNodeId();
//}



