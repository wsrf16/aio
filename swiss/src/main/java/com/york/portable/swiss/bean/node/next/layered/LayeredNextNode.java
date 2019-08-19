package com.york.portable.swiss.bean.node.next.layered;

import com.york.portable.swiss.resource.ClassUtils;

public interface LayeredNextNode<T> {
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

    LayeredNextNode<T> getNext();

    void setNext(LayeredNextNode<T> next);

    LayeredNextNode<T> getPrev();

    void setPrev(LayeredNextNode<T> prev);

    default boolean head() {
        return getPrev() == null;
    }

    default boolean tail() {
        return getNext() == null;
    }

//    Function<Class<? extends NextNode<?>>, NextNode<?>> newInstance1 = clazz -> ClassUtils.newInstance(clazz);

}
