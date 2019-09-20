package com.aio.portable.swiss.bean.node.next.tiled;

import com.aio.portable.swiss.bean.node.NextNode;
import com.aio.portable.swiss.bean.node.PrevNode;
import com.aio.portable.swiss.resource.ClassWorld;
import org.springframework.beans.BeanUtils;

public interface TiledNextNode extends NextNode<TiledNextNode>, PrevNode<TiledNextNode> {
    String[] IGNORE_PROPERTIES = {"prev", "next"};

    static <T extends TiledNextNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassWorld.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    default void isolate() {
        setPrev(null);
        setNext(null);
    }
}
