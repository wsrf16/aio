package com.york.portable.swiss.bean.node.relation.tiled;

import com.york.portable.swiss.bean.node.relation.RelationNode;
import com.york.portable.swiss.bean.node.relation.layered.LayeredRelationNode;
import com.york.portable.swiss.resource.ClassUtils;
import com.york.portable.swiss.sugar.PropertyExtra;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

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
