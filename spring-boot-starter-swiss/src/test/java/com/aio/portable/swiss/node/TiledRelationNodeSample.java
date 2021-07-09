package com.aio.portable.swiss.node;

import com.aio.portable.swiss.suite.bean.node.relation.tiled.TiledRelationNode;

public class TiledRelationNodeSample<ID> extends TiledRelationNode<ID> {
    String name;

    public TiledRelationNodeSample(ID nodeId, ID nextNodeid, String name) {
        this.name = name;
        setNodeId(nodeId);
        setNextNodeId(nextNodeid);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
