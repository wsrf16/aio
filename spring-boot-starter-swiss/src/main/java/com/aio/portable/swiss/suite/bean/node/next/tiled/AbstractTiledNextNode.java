package com.aio.portable.swiss.suite.bean.node.next.tiled;

public abstract class AbstractTiledNextNode implements TiledNextNode {
    TiledNextNode next;
    TiledNextNode prev;

    @Override
    public TiledNextNode getNext() {
        return next;
    }

    @Override
    public void setNext(TiledNextNode next) {
        this.next = next;
    }

    @Override
    public TiledNextNode getPrev() {
        return prev;
    }

    @Override
    public void setPrev(TiledNextNode prev) {
        this.prev = prev;
    }
}

