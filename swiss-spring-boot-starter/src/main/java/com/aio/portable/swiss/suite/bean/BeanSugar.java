package com.aio.portable.swiss.suite.bean;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.meta.function.LambdaSupplier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class BeanSugar {

    public static final <T, U> boolean match(T source, U match) {
        if (match == null)
            return true;
        Map<String, Object> sourceMap = ClassSugar.PropertyDescriptors.toNameValueMap(source);
        Map<String, Object> matchMap = ClassSugar.PropertyDescriptors.toNameValueMap(match);
        return match(sourceMap, matchMap);
    }

    public static final <U> boolean match(Map<String, Object> source, U match) {
        if (match == null)
            return true;
        Map<String, Object> matchMap = ClassSugar.PropertyDescriptors.toNameValueMap(match);
        return mapMatch(source, matchMap);
    }

    public static final <T> boolean match(T source, LambdaSupplier<?>... condition) {
        Map<String, Object> sourceMap = ClassSugar.PropertyDescriptors.toNameValueMap(source);
        return match(sourceMap, condition);
    }

    public static final boolean match(Map<String, Object> source, LambdaSupplier<?>... condition) {
        Stream<LambdaSupplier<?>> fnStream = Arrays.stream(condition);
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

//    public static final <T> Boolean match(T bean, T match) {
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

    private static final boolean mapMatch(Map<String, Object> source, Map<String, Object> matchMap) {
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
    public static final <T> boolean anyMatch(Collection<T> source, Collection<T> matchList) {
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
    public static final <T> boolean allMatch(Collection<T> source, Collection<T> matchList) {
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
    public static final <T> boolean noneMatch(Collection<T> source, Collection<T> matchList) {
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
