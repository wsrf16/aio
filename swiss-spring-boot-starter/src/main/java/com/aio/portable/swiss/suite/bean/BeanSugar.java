package com.aio.portable.swiss.suite.bean;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.meta.function.InstanceGetter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class BeanSugar {

    public static <T, U> boolean match(T source, U match) {
        if (match == null)
            return true;
        Map<String, Object> sourceMap = ClassSugar.PropertyDescriptors.toNameValueMap(source);
        Map<String, Object> matchMap = ClassSugar.PropertyDescriptors.toNameValueMap(match);
        return match(sourceMap, matchMap);
    }

    public static <U> boolean match(Map<String, Object> source, U match) {
        if (match == null)
            return true;
        Map<String, Object> matchMap = ClassSugar.PropertyDescriptors.toNameValueMap(match);
        return mapMatch(source, matchMap);
    }

    public static <T> boolean match(T source, InstanceGetter<?>... condition) {
        Map<String, Object> sourceMap = ClassSugar.PropertyDescriptors.toNameValueMap(source);
        return match(sourceMap, condition);
    }

    public static boolean match(Map<String, Object> source, InstanceGetter<?>... condition) {
        Stream<InstanceGetter<?>> fnStream = Arrays.stream(condition);
        boolean match = fnStream
                .filter(fn -> {
                    String propertyName = ClassSugar.PropertyDescriptors.getPropertyNameOf(fn);
                    return source.containsKey(propertyName);
                })
                .filter(fn -> fn.get() != null)
                .allMatch(fn -> {
                    String propertyName = ClassSugar.PropertyDescriptors.getPropertyNameOf(fn);
                    Object itemPropertyValue = source.get(propertyName);
                    Object conditionValue = fn.get();

                    return Objects.equals(itemPropertyValue, conditionValue);
                });
        return match;
    }

//    public static <T> Boolean match(T bean, T match) {
//        if (match == null)
//            return true;
//        else {
//            if (bean == null)
//                return false;
//            else {
//                Map<String, Object> nameValueMatch = ClassSugar.PropertyDescriptors.toNameValueMap(match);
//                Map<String, Object> nameValueBean = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
//
//                boolean equal = mapMatch(nameValueMatch, nameValueBean);
//                return equal;
//            }
//        }
//    }

    private static boolean mapMatch(Map<String, Object> source, Map<String, Object> matchMap) {
        if (matchMap == null)
            return true;
        boolean matched = matchMap.entrySet().stream()
                .filter(c -> c.getValue() != null)
                .filter(c -> source.containsKey(c.getKey()))
                .allMatch(c -> Objects.equals(source.get(c.getKey()), c.getValue()));
        return matched;

//        return source.entrySet().stream().allMatch(key -> {
//            Boolean b;
//            SUB subMatch = (SUB) source.get(key);
//            SUB subBean = (SUB) matchMap.get(key);
//            if (subMatch == null)
//                b = true;
//            else if (subBean == null)
//                b = false;
//            else {
//                Class<SUB> subClazz = (Class<SUB>) subMatch.getClass();
//                if (ClassSugar.isPrimitiveOrWrapper(subClazz))
//                    b = Objects.equals(subBean, subMatch);
//                else if ((List.class).isAssignableFrom(subClazz))
//                    b = anyMatch((List) subBean, (List) subMatch);
//                else
//                    b = match(subBean, subMatch);
//            }
//            return b;
//        });
    }

    /**
     * anyMatch : like Objects.deepEquals()
     *
     * @param source
     * @param matchList
     * @param <T>
     * @return
     */
    public static <T> boolean anyMatch(Collection<T> source, Collection<T> matchList) {
        boolean equal = matchList.stream().anyMatch(c -> BeanSugar.match(source, c));
        return equal;

//        Optional<T> first = matchList.stream().findFirst();
//        Map<String, Object> nameValueMatch = ClassSugar.PropertyDescriptors.toNameValueMap(first.get());
//        boolean equal = source.stream().anyMatch(bean -> {
//            Map<String, Object> nameValueBean = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
//
//            boolean itemEqual = mapMatch(nameValueMatch, nameValueBean);
//            return itemEqual;
//        });
//        return equal;
    }

    /**
     * allMatch
     * @param source
     * @param matchList
     * @param <T>
     * @return
     */
    public static <T> boolean allMatch(Collection<T> source, Collection<T> matchList) {
        boolean equal = matchList.stream().allMatch(c -> BeanSugar.match(source, c));
        return equal;

//        Optional<T> first = matchList.stream().findFirst();
//        Map<String, Object> nameValueMatch = ClassSugar.PropertyDescriptors.toNameValueMap(first.get());
//        boolean equal = source.stream().allMatch(bean -> {
//            Map<String, Object> nameValueBean = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
//
//            boolean itemEqual = mapMatch(nameValueMatch, nameValueBean);
//            return itemEqual;
//        });
//        return equal;
    }

    /**
     * noneMatch
     * @param source
     * @param matchList
     * @param <T>
     * @return
     */
    public static <T> boolean noneMatch(Collection<T> source, Collection<T> matchList) {
        boolean equal = matchList.stream().noneMatch(c -> BeanSugar.match(source, c));
        return equal;

//        Optional<T> first = matchList.stream().findFirst();
//        Map<String, Object> nameValueMatch = ClassSugar.PropertyDescriptors.toNameValueMap(first.get());
//        boolean equal = source.stream().noneMatch(bean -> {
//            Map<String, Object> nameValueBean = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
//
//            boolean itemEqual = mapMatch(nameValueMatch, nameValueBean);
//            return itemEqual;
//        });
//        return equal;
    }












}
