package com.aio.portable.swiss.bean.node.relation.tiled;

import com.aio.portable.swiss.bean.node.relation.RelationNode;
import com.aio.portable.swiss.resource.ClassUtils;
import com.aio.portable.swiss.bean.node.relation.layered.LayeredRelationNode;
import org.springframework.beans.BeanUtils;

public interface TiledRelationNode<ID> extends RelationNode<ID> {
    String[] IGNORE_PROPERTIES = {"nodeId", "nextNodeId"};

    static <T extends TiledRelationNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassUtils.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    static <RETURN extends LayeredRelationNode, SRC extends TiledRelationNode> RETURN toLayeredRelationNode(Class<RETURN> clazz, SRC item) {
        RETURN returnT = ClassUtils.newInstance(clazz);
        BeanUtils.copyProperties(item, returnT, IGNORE_PROPERTIES);
        return returnT;
    }

}
