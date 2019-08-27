package com.york.portable.swiss.bean.node.next.layered;

import com.york.portable.swiss.bean.node.NextNode;
import com.york.portable.swiss.bean.node.PrevNode;
import com.york.portable.swiss.resource.ClassUtils;

public interface LayeredNextNode<T> extends NextNode<LayeredNextNode<T>>, PrevNode<LayeredNextNode<T>> {
    static <R extends LayeredNextNode> R newInstance(Class<R> clazz) {
        return ClassUtils.newInstance(clazz);
    }

    static <T, R extends LayeredNextNode> R newInstance(Class<R> clazz, T item) {
        R t = ClassUtils.newInstance(clazz);
        t.setItem(item);
        return t;
    }

    T getItem();

    void setItem(T item);

    //    Function<Class<? extends NextNode<?>>, NextNode<?>> newInstance1 = clazz -> ClassUtils.newInstance(clazz);

}
