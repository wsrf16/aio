package com.aio.portable.swiss.suite.bean.node.linked;

import com.aio.portable.swiss.sugar.meta.ClassSugar;

public class ReferenceLinkedNode<T> implements NextNode<ReferenceLinkedNode<T>>, PrevNode<ReferenceLinkedNode<T>> {
    private T data;
    private ReferenceLinkedNode<T> next;
    private ReferenceLinkedNode<T> prev;

    public ReferenceLinkedNode(ReferenceLinkedNode<T> prev, T data, ReferenceLinkedNode<T> next) {
        setPrev(prev);
        setData(data);
        setNext(next);
    }

    public ReferenceLinkedNode(T data) {
        setData(data);
    }

    public ReferenceLinkedNode() {
    }

//    @Override
    public T getData() {
        return data;
    }

//    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public ReferenceLinkedNode<T> getNext() {
        return next;
    }

    @Override
    public void setNext(ReferenceLinkedNode<T> next) {
        this.next = next;
    }

    @Override
    public ReferenceLinkedNode<T> getPrev() {
        return prev;
    }

    @Override
    public void setPrev(ReferenceLinkedNode<T> prev) {
        this.prev = prev;
    }









    public static <R extends ReferenceLinkedNode> R newInstance(Class<R> clazz) {
        return ClassSugar.newInstance(clazz);
    }

    public static <T, R extends ReferenceLinkedNode<T>> R newInstance(Class<R> clazz, T item) {
        R r = ClassSugar.newInstance(clazz);
        r.setData(item);
        return r;
    }

    //    Function<Class<? extends NextNode<?>>, NextNode<?>> newInstance1 = clazz -> ClassUtils.newInstance(clazz);
}


