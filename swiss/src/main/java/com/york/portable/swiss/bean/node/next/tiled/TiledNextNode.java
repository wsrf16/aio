package com.york.portable.swiss.bean.node.next.tiled;

import com.york.portable.swiss.bean.node.NextNode;
import com.york.portable.swiss.bean.node.PrevNode;
import com.york.portable.swiss.resource.ClassUtils;
import org.springframework.beans.BeanUtils;

public interface TiledNextNode extends NextNode<TiledNextNode>, PrevNode<TiledNextNode> {
    String[] IGNORE_PROPERTIES = {"prev", "next"};

    static <T extends TiledNextNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassUtils.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    default void isolate() {
        setPrev(null);
        setNext(null);
    }
}
