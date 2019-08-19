package com.york.portable.park.unit.swiss.node;

import com.york.portable.swiss.bean.node.next.tiled.AbstractTiledNextNode;
import com.york.portable.swiss.bean.node.next.tiled.TiledNextNode;

public class TiledNextNodeBean extends AbstractTiledNextNode {
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
