package com.aio.portable.swiss.sugar.type;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
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
    public static final <T> T[] removeEnd(T[] tables) {
        T[] remain = Arrays.copyOf(tables, tables.length - 1);
        return remain;
    }

    public static final <T> T getEnd(T[] tables) {
        T end = tables[tables.length - 1];
        return end;
    }

    public static final boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
//        return ObjectUtils.isEmpty(array);
    }

    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public static final boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
//        return org.springframework.util.CollectionUtils.isEmpty(collection);
    }


    public static final long longOf(Object[] array) {
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
    public static final <T> Stream<T> except(final Collection<T> collection1, final Collection<T> collection2) {
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

    public static <T> boolean anyEquals(final Collection<T> source, final Collection<T> target) {
        boolean anyMatch = source.stream().anyMatch(src -> target.contains(src));
        return anyMatch;
    }

    /**
     * containsAll
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> boolean containsAll(final Collection<T> source, final Collection<T> target) {
//        boolean exist = source.stream().anyMatch(src -> target.contains(src));
//        return exist;
        return (source == target) || (source != null && source.containsAll(target));
    }


//    /**
//     * 并集，不去重
//     *
//     * @param collection1
//     * @param collection2
//     * @return
//     */
//    public static final <T> List<T> concat(Collection<T> collection1, Collection<T> collection2) {
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
    public static final <T> List<T> concat(Collection<T>... collections) {
        List<T> list = new ArrayList<>();
        for (Collection<T> collection : collections) {
            list.addAll(collection);
        }
        return list;
    }

    /**
     * union 并集，去重
     *
     * @param collections
     * @return
     */
    public static final <T> Stream<T> union(Collection<T>... collections) {
        return concat(collections).stream().distinct();
    }


    public static <T> T[] clone(T[] array) {
        return array == null ? null : array.clone();
    }

    /**
     * replaceKey
     * @param map
     * @param oldKey
     * @param newKey
     * @param <K>
     * @param <V>
     */
    public static final <K, V> void replaceKey(Map<K, V> map, K oldKey, K newKey) {
        map.put(newKey, map.remove(oldKey));
    }


    /**
     * concatIfAbsent 并集去重
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Map<K, V> concatIfAbsent(Map<K, V>... maps) {
//        Map<K, V> map = new HashMap<>();
//        for (Map<K, V> item : maps) {
//            item.entrySet().forEach(c -> {
//                map.putIfAbsent(c.getKey(), c.getValue());
//            });
//        }
//        return map;
        return concatIfAbsent((item) -> true, maps);
    }

    /**
     * concatIfAbsent 并集去重
     * @param maps
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Map<K, V> concatIfAbsent(Predicate<Map.Entry<K, V>> predicate, Map<K, V>... maps) {
        Map<K, V> map = new HashMap<>();
        for (Map<K, V> item : maps) {
            item.entrySet().forEach(c -> {
                if (predicate.test(c))
                    map.putIfAbsent(c.getKey(), c.getValue());
            });
        }
        return map;
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
    public static final <T> List<T> copy(List<? extends T> src) {
        List<T> dest = new ArrayList<>(Arrays.asList((T[]) new Object[src.size()]));
        java.util.Collections.copy(dest, src);
        return dest;
    }

//    /**
//     * concat
//     * @param generator: String[]::new
//     * @param src
//     * @param <T>
//     * @return
//     */
//    public static final <T> T[] concat(IntFunction<T[]> generator, T[] src, T... dest) {
////        int length1 = src.length;
////        int length2 = dest.length;
////
////        int length = length1 + length2;
////        T[] result = (T[]) new Object[length];
////        for (int i = 0; i < length1; i++) {
////            result[i] = src[i];
////        }
////        for (int i = length1; i < length; i++) {
////            result[i] = dest[length - length1 - 1];
////        }
//        List<T> list1 = Arrays.stream(src).collect(Collectors.toList());
//        List<T> list2 = Arrays.stream(dest).collect(Collectors.toList());
//        return concat(list1, list2).stream().toArray(generator);
//    }

    /**
     * concat
     * @param array1
     * @param array2
     * @param <T>
     * @return
     */
    public static final <T> T[] concat(T[] array1, T[]... array2) {
        T[] s = array1;
        for(T[] t : array2) {
           s = concat(s, t);
        }
        return s;
    }

    /**
     * concat
     * @param array1
     * @param array2
     * @param <T>
     * @return
     */
    public static final <T> T[] concat(T[] array1, T... array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        } else {
            Class<?> type1 = array1.getClass().getComponentType();
            T[] joinedArray = (T[])(Array.newInstance(type1, array1.length + array2.length));
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);

            try {
                System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
                return joinedArray;
            } catch (ArrayStoreException var6) {
                Class<?> type2 = array2.getClass().getComponentType();
                if (!type1.isAssignableFrom(type2)) {
                    throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), var6);
                } else {
                    throw var6;
                }
            }
        }
    }

    /**
     * cloneByProperties
     * @param srcCollection
     * @param targetItem
     * @param <S>
     * @param <T>
     * @return
     */
    public static final <S, T> List<T> cloneByProperties(Collection<S> srcCollection, Class<T> targetItem) {
        List<T> dest = new ArrayList<>();
        for (S s : srcCollection) {
            T t = null;
            try {
                t = (T) targetItem.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
            BeanUtils.copyProperties(s, t);
            dest.add(t);
        }
        return dest;
    }


    public static final <T> List<T> arrayToList(Object source) {
        return CollectionUtils.arrayToList(source);
    }

    public static <T> ArrayList<T> newArrayList(T... ts) {
        ArrayList<T> tArrayList = new ArrayList<>();
        for (T t: ts) {
            tArrayList.add(t);
        }
        return tArrayList;
    }


    private static final <T> List<T> toList(Iterator<T> iterator, int estimatedSize) {
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

    public static final Object[] toArray(Object source) {
        return ObjectUtils.toObjectArray(source);
    }

//    public static final List tolist(Iterator iterator) {
//        return IteratorUtils.toList(iterator);
//    }

    public static final <T> List<T> toList(Iterator<T> iterator) {
        return toList(iterator, 128);
    }

    public static final <T> List<T> asList(T... a) {
        return new ArrayList<>(Arrays.asList(a));
    }

    public static final <T> ArrayList<T> toList(Enumeration<T> e) {
        return java.util.Collections.list(e);
    }

    public static final <T> Enumeration<T> toEnumeration(final Collection<T> c) {
        return java.util.Collections.enumeration(c);
    }

//    public static final Object[] toArray(List<?> list) {
//        return list.stream().toArray();
//    }

    public static final <T> T[] toArray(@NotNull List<T> list, IntFunction<T[]> generator) {
        return list.stream().toArray(generator);
    }

    public static final <T> T[] toArrayNullable(@NotNull List<T> list) {
        return list.size() < 1 ? null : toArray(list, (Class<T>)list.get(0).getClass());
    }

    public static final <T> T[] toArray(@NotNull List<T> list, @NotNull Class<T> componentType) {
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
    public static final <T> T[] toArray(List<T> list, T[] t) {
        return list.toArray(t);
    }

    public static final <T, K, V> HashMap<K, V> toMap(Stream<T> stream, Function<T, K> keyMapper, Function<T, V> valueMapper) {
        HashMap<K, V> map = stream.collect(HashMap::new, (_map, _entity) -> _map.put(keyMapper.apply(_entity), valueMapper.apply(_entity)), HashMap::putAll);
        return map;
    }

    public static final <T> List<T> flatNextToList(T t, Function<T, T> getNextFunction) {
        List<T> list = new ArrayList<>();
        while (t != null) {
            list.add(t);
            t = getNextFunction.apply(t);
        }
        return list;
    }

    public static final <T> Map<T, Long> groupCount(List<T> list) {
        Map<T, Long> map = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return map;
    }

    public static final String join(List<String> list, String delimiter) {
        return list.stream().collect(Collectors.joining(delimiter));
    }


}
