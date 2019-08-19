package com.york.portable.swiss.bean.node.relation;

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
}
