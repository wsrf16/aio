package com.york.portable.swiss.bean.node.relation.tiled;

import com.york.portable.swiss.bean.node.next.layered.LayeredNextNode;
import com.york.portable.swiss.bean.node.next.tiled.TiledNextNode;
import com.york.portable.swiss.bean.node.relation.RelationNode;
import com.york.portable.swiss.bean.node.relation.layered.LayeredRelationNode;
import com.york.portable.swiss.resource.ClassUtils;
import com.york.portable.swiss.sugar.PropertyExtra;
import org.springframework.beans.BeanUtils;

import java.util.stream.Collectors;

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
