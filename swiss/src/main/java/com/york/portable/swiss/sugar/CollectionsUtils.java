package com.york.portable.swiss.sugar;

import org.apache.commons.collections.IteratorUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionsUtils {
    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public final static boolean isEmpty(Collection<?> collection) {
//        return list == null || list.isEmpty();
        return CollectionUtils.isEmpty(collection);
    }

    public final static boolean isEmpty(Object[] array) {
//        return array == null || array.length < 1;
        return ObjectUtils.isEmpty(array);
    }


    /**
     * 交集
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public final static List<String> intersect(Collection<String> collection1, Collection<String> collection2) {
        List<String> list = collection1.stream().filter(item -> collection2.contains(item)).collect(Collectors.toList());
        return list;
    }

    /**
     * 差集，从集合1去除集合2
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public final static List<String> except(Collection<String> collection1, Collection<String> collection2) {
        List<String> list = collection1.stream().filter(item -> !collection2.contains(item)).collect(Collectors.toList());
        return list;
    }

    /**
     * 并集，不去重
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public final static List<String> concat(Collection<String> collection1, Collection<String> collection2) {
        List<String> _list1 = collection1.stream().collect(Collectors.toList());
        List<String> _list2 = collection2.stream().collect(Collectors.toList());
        _list1.addAll(_list2);
        List<String> list = _list1;
        return list;
    }

    /**
     * 并集，去重
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public final static List<String> union(Collection<String> collection1, Collection<String> collection2) {
        List<String> list = concat(collection1, collection2).stream().distinct().collect(Collectors.toList());
        return list;
    }

    /**
     * copy
     * @param src
     * @param <T>
     * @return
     */
    public final static <T> List<T> copy(List<? extends T> src) {
        List<T> dest = new ArrayList<>(Arrays.asList((T[]) new Object[src.size()]));
        Collections.copy(dest, src);
        return dest;
    }

    public final static <S, T> List<T> copyProperties(List<? extends S> src, Class clazz) {
        List<T> dest = new ArrayList<>();
        for (S s : src) {
            T t = null;
            try {
                t = (T)clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            BeanUtils.copyProperties(s, t);
            dest.add(t);
        }
        return dest;
    }

    public final static List tolist(Iterator iterator) {
        return IteratorUtils.toList(iterator);
    }

    public final static <T> ArrayList<T> tolist(Enumeration<T> e) {
        return Collections.list(e);
    }

    public final static <T> Enumeration<T> toEnumeration(final Collection<T> c) {
        return Collections.enumeration(c);
    }

    private static void taolu() {
        {
            Iterator<String> iterator = Collections.emptyIterator();
            List<String> list = IteratorUtils.toList(iterator);
        }
        {
            Enumeration<String> enumeration = Collections.emptyEnumeration();
            List<String> arrayList = Collections.list(enumeration);
        }
        {
            List<String> arrayList = Collections.emptyList();
            Enumeration<String> enumeration = Collections.enumeration(arrayList);
        }
    }
}
