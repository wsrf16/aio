package com.aio.portable.swiss.suite.bean.node.relation.tiled;

import com.aio.portable.swiss.suite.bean.node.relation.RelationNode;
import com.aio.portable.swiss.suite.bean.node.relation.layered.LayeredRelationNode;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.springframework.beans.BeanUtils;

public abstract class TiledRelationNode<ID> implements RelationNode<ID> {
    private final static String[] IGNORE_PROPERTIES = {"nodeId", "nextNodeId"};

    static <T extends TiledRelationNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassSugar.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    static <RETURN extends LayeredRelationNode, SRC extends TiledRelationNode> RETURN toLayeredRelationNode(Class<RETURN> clazz, SRC item) {
        RETURN returnT = ClassSugar.newInstance(clazz);
        BeanUtils.copyProperties(item, returnT, IGNORE_PROPERTIES);
        return returnT;
    }












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
