package com.york.portable.park.unit.swiss.node;

import com.york.portable.swiss.bean.node.relation.tiled.AbstractTiledRelationNode;
import com.york.portable.swiss.bean.node.relation.tiled.TiledRelationNode;

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
