package com.aio.portable.swiss.sugar;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public abstract class SPISugar {
    public final static <T> List<T> load(Class<T> interfaze) {
        Iterator<T> iterator = ServiceLoader.load(interfaze).iterator();
        List<T> list = CollectionSugar.toList(iterator);
        return list;
    }

}
