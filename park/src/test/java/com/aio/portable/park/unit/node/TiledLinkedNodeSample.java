package com.aio.portable.park.unit.node;

import com.aio.portable.swiss.suite.bean.node.linked.tiled.TiledLinkedNode;

public class TiledLinkedNodeSample extends TiledLinkedNode {
    private String name;

    public TiledLinkedNodeSample() {}

    public TiledLinkedNodeSample(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
