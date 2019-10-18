package com.aio.portable.swiss.structure.bean.node.next.tiled;

import com.aio.portable.swiss.structure.bean.node.NextNode;
import com.aio.portable.swiss.structure.bean.node.PrevNode;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.springframework.beans.BeanUtils;

public interface TiledNextNode extends NextNode<TiledNextNode>, PrevNode<TiledNextNode> {
    String[] IGNORE_PROPERTIES = {"prev", "next"};

    static <T extends TiledNextNode> T newInstance(Class<T> clazz, Object item) {
        T t = ClassSugar.newInstance(clazz);
        BeanUtils.copyProperties(item, t, IGNORE_PROPERTIES);
        return t;
    }

    default void isolate() {
        setPrev(null);
        setNext(null);
    }
}
