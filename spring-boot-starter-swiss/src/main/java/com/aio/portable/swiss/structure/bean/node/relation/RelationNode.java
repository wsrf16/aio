package com.aio.portable.swiss.structure.bean.node.relation;

public interface RelationNode<ID> {
//    T getItem();

//    void setItem(T item);

    ID getNodeId();

    void setNodeId(ID nodeId);

    ID getNextNodeId();

    void setNextNodeId(ID nextNodeId);

    default boolean tail() {
        return getNextNodeId() == null;
    }

    default boolean hasNext() {
        return getNextNodeId() != null;
    }

}
