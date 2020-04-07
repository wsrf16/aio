package com.aio.portable.swiss.suite.bean.node.relation.layered;

import com.aio.portable.swiss.suite.bean.node.relation.RelationNode;

public class LayeredRelationNode<ID, T> implements RelationNode<ID> {
    //    static <R, T extends LayeredNextNode> LayeredRelationNode<R> newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }


//    static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>> T newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }











    T item;
    ID nodeId;
    ID nextNodeId;

    public LayeredRelationNode(T item, ID nodeId, ID nextNodeId) {
        setItem(item);
        setNodeId(nodeId);
        setNextNodeId(nextNodeId);
    }

    public LayeredRelationNode(T item, ID nodeId) {
        setItem(item);
        setNodeId(nodeId);
    }

//    @Override
    public T getItem() {
        return item;
    }

//    @Override
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



