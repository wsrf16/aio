package com.aio.portable.swiss.bean.node.next.layered;

import com.aio.portable.swiss.bean.node.NextNode;
import com.aio.portable.swiss.bean.node.PrevNode;
import com.aio.portable.swiss.resource.ClassWorld;

public interface LayeredNextNode<T> extends NextNode<LayeredNextNode<T>>, PrevNode<LayeredNextNode<T>> {
    static <R extends LayeredNextNode> R newInstance(Class<R> clazz) {
        return ClassWorld.newInstance(clazz);
    }

    static <T, R extends LayeredNextNode<T>> R newInstance(Class<R> clazz, T item) {
        R r = ClassWorld.newInstance(clazz);
        r.setItem(item);
        return r;
    }

    T getItem();

    void setItem(T item);

    //    Function<Class<? extends NextNode<?>>, NextNode<?>> newInstance1 = clazz -> ClassUtils.newInstance(clazz);

}
