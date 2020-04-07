package com.aio.portable.swiss.suite.bean.node.linked.tiled;

import com.aio.portable.swiss.suite.bean.node.linked.LinkedNode;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.springframework.beans.BeanUtils;

public abstract class TiledLinkedNode implements LinkedNode<TiledLinkedNode> {
    TiledLinkedNode next;
    TiledLinkedNode prev;

    @Override
    public TiledLinkedNode getNext() {
        return next;
    }

    @Override
    public void setNext(TiledLinkedNode next) {
        this.next = next;
    }

    @Override
    public TiledLinkedNode getPrev() {
        return prev;
    }

    @Override
    public void setPrev(TiledLinkedNode prev) {
        this.prev = prev;
    }



    protected final static String[] IGNORE_PROPERTIES = {"prev", "next"};

    public static <T extends TiledLinkedNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassSugar.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    public void isolate() {
        setPrev(null);
        setNext(null);
    }
}

