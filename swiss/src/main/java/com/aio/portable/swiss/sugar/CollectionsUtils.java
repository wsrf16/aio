package com.aio.portable.swiss.sugar;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
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
     * distinctBy
     * @param list
     * @param getPropertyFunction T::getId
     * @param <T>
     * @return
     */
    public static  <T> List<T> distinctBy(List<T> list, Function<? super T, ?> getPropertyFunction) {
        List<T> collect = list.parallelStream().filter(falseIfRepeat(getPropertyFunction)).collect(Collectors.toList());
        return collect;
    }

    /**
     * repeatBy
     * @param list
     * @param getPropertyFunction T::getId
     * @param <T>
     * @return
     */
    public static  <T> boolean repeatBy(List<T> list, Function<? super T, ?> getPropertyFunction) {
        boolean b = list.parallelStream().anyMatch(falseIfRepeat(getPropertyFunction));
        return b;
    }

    /**
     * falseIfRepeat
     * @param getPropertyFunction User::getName
     * @param <T>
     * @return
     */
    private static  <T> Predicate<T> falseIfRepeat(Function<? super T, ?> getPropertyFunction) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(getPropertyFunction.apply(t));
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

    /**
     * copyProperties
     * @param src
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public final static <S, T> List<T> copyPropertiesToNewList(List<S> src, Class target) {
        List<T> dest = new ArrayList<>();
        for (S s : src) {
            T t = null;
            try {
                t = (T) target.newInstance();
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



//    public final static List tolist(Iterator iterator) {
//        return IteratorUtils.toList(iterator);
//    }

    private final static List toList(Iterator iterator, int estimatedSize) {
        if (iterator == null) {
            throw new NullPointerException("Iterator must not be null");
        } else if (estimatedSize < 1) {
            throw new IllegalArgumentException("Estimated size must be greater than 0");
        } else {
            ArrayList list = new ArrayList(estimatedSize);

            while(iterator.hasNext()) {
                list.add(iterator.next());
            }

            return list;
        }
    }

    public final static List toList(Iterator iterator) {
        return toList(iterator, 10);
    }


    public final static <T> ArrayList<T> toList(Enumeration<T> e) {
        return Collections.list(e);
    }

    public final static <T> Enumeration<T> toEnumeration(final Collection<T> c) {
        return Collections.enumeration(c);
    }

    public final static <T> List<T> flatNextToList(T t, Function<T, T> getNextFunction) {
        List<T> list = new ArrayList<>();
        while (t != null) {
            list.add(t);
            t = getNextFunction.apply(t);
        }
        return list;
    }

    private static void taolu() {
        {
            Iterator<String> iterator = Collections.emptyIterator();
            List<String> list = CollectionsUtils.toList(iterator);
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
