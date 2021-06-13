package com.aio.portable.swiss.sugar;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CollectionSugar {
    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public final static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
//        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }

    public final static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
//        return ObjectUtils.isEmpty(array);
    }

    public final static long longOf(Object[] array) {
        return array == null ? 0 : array.length;
    }

    /**
     * Create a reversed Stream from a List
     * <pre>
     * {@code
     * StreamSugar.reversedStream(asList(1,2,3))
     * .map(i->i*100)
     * .forEach(System.out::println);
     * assertThat(StreamSugar.reversedStream(Arrays.asList(1,2,3)).collect(CyclopsCollectors.toList())
     * ,equalTo(Arrays.asList(3,2,1)));
     *
     * }
     * </pre>
     *
     * @param list from to create a reversed
     * @return Reversed Stream
     */
    public static <T> Stream<T> reverse(final List<T> list) {
        return StreamSugar.reverse(list.stream());
    }


    /**
     * 差集，从集合1去除集合2
     *
     * @param collection1
     * @param collection2
     * @return
     */
    public final static <T> Stream<T> except(final Collection<T> collection1, final Collection<T> collection2) {
        Stream<T> stream = collection1.stream().filter(item -> !collection2.contains(item));
        return stream;
    }


    /**
     * except
     * @param source
     * @param target
     * @param equalFunction
     * @param <T>
     * @return
     */
    public static <T> Stream<T> except(final List<T> source, final List<T> target, BiFunction<T, T, Boolean> equalFunction) {
        Stream<T> stream = source.stream().filter(src -> !target.stream().anyMatch(tgt -> equalFunction.apply(src, tgt)));
        return stream;
    }


    /**
     * intersect
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> Stream<T> intersect(final Collection<T> source, final Collection<T> target) {
        Stream<T> stream = source.stream().filter(c -> target.contains(c));
        return stream;
    }

    /**
     * intersect
     * @param source
     * @param target
     * @param equalFunction
     * @param <T>
     * @return
     */
    public static <T> Stream<T> intersect(final Collection<T> source, final Collection<T> target, BiFunction<T, T, Boolean> equalFunction) {
        Stream<T> stream = source.stream().filter(src -> target.stream().anyMatch(tgt -> equalFunction.apply(src, tgt)));
        return stream;
    }



//    /**
//     * 并集，不去重
//     *
//     * @param collection1
//     * @param collection2
//     * @return
//     */
//    public final static <T> List<T> concat(Collection<T> collection1, Collection<T> collection2) {
//        List<T> _list1 = collection1.stream().collect(Collectors.toList());
//        List<T> _list2 = collection2.stream().collect(Collectors.toList());
//        _list1.addAll(_list2);
//        List<T> list = _list1;
//        return list;
//    }


    /**
     * concat 并集，不去重
     * @param collections
     * @param <T>
     * @return
     */
    public final static <T> List<T> concat(Collection<T>... collections) {
        List<T> _list = new ArrayList<>();
        for (Collection<T> collection : collections) {
            if (collection != null)
                _list.addAll(collection.stream().collect(Collectors.toList()));
        }
        return _list;
    }


    /**
     * union 并集，去重
     *
     * @param collections
     * @return
     */
    public final static <T> List<T> union(Collection<T> collections) {
        List<T> _list = concat(collections).stream().distinct().collect(Collectors.toList());
        return _list;
    }

    /**
     * concatIfAbsent 并集去重
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public final static <K, V> Map<K, V> concatIfAbsent(Map<K, V>... maps) {
        Map<K, V> _map = new HashMap<>();
        for (Map<K, V> map : maps) {
            map.entrySet().forEach(c -> {
                _map.putIfAbsent(c.getKey(), c.getValue());
            });
        }
        return _map;
    }

    /**
     * concatIfAbsent 并集去重
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public final static <K, V> Map<K, V> concatIfAbsent(Predicate<Map.Entry<K, V>> predicate, Map<K, V>... maps) {
        Map<K, V> _map = new HashMap<>();
        for (Map<K, V> map : maps) {
            map.entrySet().forEach(c -> {
                if (predicate.test(c))
                    _map.putIfAbsent(c.getKey(), c.getValue());
            });
        }
        return _map;
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
     * containsAll
     * @param range
     * @param piece
     * @param <T>
     * @return
     */
    public static  <T> boolean containsAll(List<T> range, List<T> piece){
        return (range == piece) || (range != null && range.containsAll(piece));
    }

    /**
     * falseIfRepeat
     *
     * @param getPropertyFunction User::getName
     * @param <T>
     * @return
     */
    private static <T> Predicate<T> falseIfRepeat(Function<? super T, ?> getPropertyFunction) {
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
        java.util.Collections.copy(dest, src);
        return dest;
    }

    /**
     * add
     * @param src
     * @param <T>
     * @return
     */
    public final static <T> T[] concat(IntFunction<T[]> generator, T[] src, T... dest) {
//        int length1 = src.length;
//        int length2 = dest.length;
//
//        int length = length1 + length2;
//        T[] result = (T[]) new Object[length];
//        for (int i = 0; i < length1; i++) {
//            result[i] = src[i];
//        }
//        for (int i = length1; i < length; i++) {
//            result[i] = dest[length - length1 - 1];
//        }
        List<T> list1 = Arrays.stream(src).collect(Collectors.toList());
        List<T> list2 = Arrays.stream(dest).collect(Collectors.toList());
        return concat(list1, list2).stream().toArray(generator);
    }

    /**
     * cloneByProperties
     * @param srcCollection
     * @param targetItem
     * @param <S>
     * @param <T>
     * @return
     */
    public final static <S, T> List<T> cloneByProperties(Collection<S> srcCollection, Class<T> targetItem) {
        List<T> dest = new ArrayList<>();
        for (S s : srcCollection) {
            T t = null;
            try {
                t = (T) targetItem.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
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

    private final static <T> List<T> toList(Iterator<T> iterator, int estimatedSize) {
        if (iterator == null) {
            throw new NullPointerException("Iterator must not be null");
        } else if (estimatedSize < 1) {
            throw new IllegalArgumentException("Estimated size must be greater than 0");
        } else {
            ArrayList<T> list = new ArrayList<T>(estimatedSize);

            while(iterator.hasNext()) {
                list.add(iterator.next());
            }

            return list;
        }
    }

    public final static Object[] toArray(Object source) {
        return ObjectUtils.toObjectArray(source);
    }

    public final static <T> List<T> arrayToList(Object source) {
        return CollectionUtils.arrayToList(source);
    }

    public final static <T> List<T> toList(Iterator<T> iterator) {
        return toList(iterator, 10);
    }

    public final static <T> List<T> toList(T... array) {
//        List<T> list = new ArrayList<>(Arrays.asList((T[]) new Object[array.length]));
        List<T> list = new ArrayList<>(Arrays.asList(array));
        return list;
    }


    public final static <T> ArrayList<T> toList(Enumeration<T> e) {
        return java.util.Collections.list(e);
    }

    public final static <T> Enumeration<T> toEnumeration(final Collection<T> c) {
        return java.util.Collections.enumeration(c);
    }

//    public final static Object[] toArray(List<?> list) {
//        return list.stream().toArray();
//    }

    public final static <T> T[] toArray(List<T> list, IntFunction<T[]> generator) {
        return list.stream().toArray(generator);
    }

    public final static <T> T[] toArrayNullable(List<T> list) {
        return list.size() < 1 ? null : toArray(list, (Class<T>)list.get(0).getClass());
    }

    public final static <T> T[] toArray(List<T> list, Class<T> componentType) {
        T[] array = (T[])Array.newInstance(componentType, list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    /**
     * toArray
     * @param list
     * @param t eg. "new Integer[0]"
     * @param <T>
     * @return
     */
    public final static <T> T[] toArray(List<T> list, T[] t) {
        return list.toArray(t);
    }

    public final static <T, K, V> HashMap<K, V> toMap(Stream<T> stream, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        HashMap<K, V> map = stream.collect(HashMap::new, (_map, _entity) -> _map.put(keyMapper.apply(_entity), valueMapper.apply(_entity)), HashMap::putAll);
        return map;
    }

    public final static <T> List<T> flatNextToList(T t, Function<T, T> getNextFunction) {
        List<T> list = new ArrayList<>();
        while (t != null) {
            list.add(t);
            t = getNextFunction.apply(t);
        }
        return list;
    }

    public final static <T> Map<T, Long> groupCount(List<T> list) {
        Map<T, Long> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return map;
    }

    public final static String join(List<String> list, String delimiter) {
        return list.stream().collect(Collectors.joining(delimiter));
    }


}
