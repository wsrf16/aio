package com.york.portable.swiss.bean.node.relation;

import com.york.portable.swiss.bean.node.next.layered.LayeredNextNode;
import com.york.portable.swiss.resource.ClassUtils;

public interface RelationNode<ID, T> {
//    static <R, T extends LayeredNextNode> LayeredNextNode<R> newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }


//    static <ID, ITEM, T extends RelationNode<ID, ITEM>> T newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }

    T getItem();

    void setItem(T item);

    ID getNodeId();

    void setNodeId(ID id);

    ID getNextId();

    void setNextId(ID nextId);

//    default boolean head() {
//        return getNextId() == null;
//    }

    default boolean tail() {
        return getNextId() == null;
    }
}
