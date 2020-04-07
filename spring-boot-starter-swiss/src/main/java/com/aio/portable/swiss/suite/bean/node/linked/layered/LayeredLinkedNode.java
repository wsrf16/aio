package com.aio.portable.swiss.suite.bean.node.linked.layered;

import com.aio.portable.swiss.suite.bean.node.linked.LinkedNode;
import com.aio.portable.swiss.suite.resource.ClassSugar;

public class LayeredLinkedNode<T> implements LinkedNode<LayeredLinkedNode<T>> {
    T item;
    LayeredLinkedNode<T> next;
    LayeredLinkedNode<T> prev;

    public LayeredLinkedNode(LayeredLinkedNode<T> prev, T item, LayeredLinkedNode<T> next) {
        setPrev(prev);
        setItem(item);
        setNext(next);
    }

    public LayeredLinkedNode(T item) {
        setItem(item);
    }

    public LayeredLinkedNode() {
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
    public LayeredLinkedNode<T> getNext() {
        return next;
    }

//    @Override
    public void setNext(LayeredLinkedNode<T> next) {
        this.next = next;
    }

    @Override
    public LayeredLinkedNode<T> getPrev() {
        return prev;
    }

    @Override
    public void setPrev(LayeredLinkedNode<T> prev) {
        this.prev = prev;
    }









    public static <R extends LayeredLinkedNode> R newInstance(Class<R> clazz) {
        return ClassSugar.newInstance(clazz);
    }

    public static <T, R extends LayeredLinkedNode<T>> R newInstance(Class<R> clazz, T item) {
        R r = ClassSugar.newInstance(clazz);
        r.setItem(item);
        return r;
    }

    //    Function<Class<? extends NextNode<?>>, NextNode<?>> newInstance1 = clazz -> ClassUtils.newInstance(clazz);
}


