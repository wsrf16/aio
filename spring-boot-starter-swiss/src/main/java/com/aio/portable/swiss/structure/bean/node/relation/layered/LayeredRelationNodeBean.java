package com.aio.portable.swiss.structure.bean.node.relation.layered;

public class LayeredRelationNodeBean<ID, T> implements LayeredRelationNode<ID, T> {
    T item;
    ID nodeId;
    ID nextNodeId;

    public LayeredRelationNodeBean(T item, ID nodeId, ID nextNodeId) {
        setItem(item);
        setNodeId(nodeId);
        setNextNodeId(nextNodeId);
    }

    public LayeredRelationNodeBean(T item, ID nodeId) {
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
    public ID getNextNodeId() {
        return nextNodeId;
    }

    @Override
    public void setNextNodeId(ID nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
}

//interface Role {
//    <T> T getLinkedNodeId();
//}



