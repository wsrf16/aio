package com.york.portable.swiss.bean.node.relation.layered;

import com.york.portable.swiss.bean.node.relation.RelationNode;

public interface LayeredRelationNode<ID, T> extends RelationNode<ID> {
//    static <R, T extends LayeredNextNode> LayeredRelationNode<R> newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }


//    static <ID, ITEM, T extends LayeredRelationNode<ID, ITEM>> T newInstance(Class<T> clazz) {
//        return ClassUtils.newInstance(clazz);
//    }

    T getItem();

    void setItem(T item);
}
