package com.aio.portable.swiss.bean.node.relation.tiled;

public abstract class AbstractTiledRelationNode<ID> implements TiledRelationNode<ID> {
    ID nodeId;
    ID nextNodeId;

    @Override
    public ID getNodeId() {
        return nodeId;
    }

    @Override
    public void setNodeId(ID nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    public ID getNextNodeId() {
        return nextNodeId;
    }

    @Override
    public void setNextNodeId(ID nextNodeId) {
        this.nextNodeId = nextNodeId;
    }
}
