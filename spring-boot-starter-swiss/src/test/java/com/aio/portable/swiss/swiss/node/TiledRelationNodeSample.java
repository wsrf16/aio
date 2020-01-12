package com.aio.portable.swiss.swiss.node;

import com.aio.portable.swiss.structure.bean.node.relation.tiled.AbstractTiledRelationNode;

public class TiledRelationNodeSample<ID> extends AbstractTiledRelationNode<ID> {
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
