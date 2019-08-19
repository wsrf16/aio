package com.york.portable.park.unit.swiss.node;

import com.york.portable.swiss.bean.node.next.tiled.AbstractTiledNextNode;

public class TiledNextNodeSample extends AbstractTiledNextNode {
    private String name;

    public TiledNextNodeSample() {}

    public TiledNextNodeSample(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
