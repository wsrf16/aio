package com.aio.portable.swiss.structure.bean.node.next.layered;

public class LayeredNextNodeBean<T> implements LayeredNextNode<T> {
    T item;
    LayeredNextNode<T> next;
    LayeredNextNode<T> prev;

    public LayeredNextNodeBean(LayeredNextNode<T> prev, T item, LayeredNextNode<T> next) {
        setPrev(prev);
        setItem(item);
        setNext(next);
    }

    public LayeredNextNodeBean(T item) {
        setItem(item);
    }

    public LayeredNextNodeBean() {
    }

    @Override
    public T getItem() {
        return item;
    }

    @Override
    public void setItem(T item) {
        this.item = item;
    }

    @Override
    public LayeredNextNode<T> getNext() {
        return next;
    }

    @Override
    public void setNext(LayeredNextNode<T> next) {
        this.next = next;
    }

    @Override
    public LayeredNextNode<T> getPrev() {
        return prev;
    }

    @Override
    public void setPrev(LayeredNextNode<T> prev) {
        this.prev = prev;
    }

}


