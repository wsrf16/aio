package com.aio.portable.swiss.sugar.type;

import java.util.Map;
import java.util.function.Function;

public abstract class MapSugar {
    public static <K, V, T> T get(Map<K, V> source, K key) {
        return (T)source.get(key);
    }

    public static <K, V> String getString(Map<K, V> source, K key) {
        return get(source, key);
//        return getParse(source, key, String::valueOf);
    }

    public static <K, V> Integer getInteger(Map<K, V> source, K key) {
        return get(source, key);
//        return getParse(source, key, Integer::parseInt);
    }

    public static <K, V> Float getFloat(Map<K, V> source, K key) {
        return get(source, key);
//        return getParse(source, key, Float::parseFloat);
    }

    public static <K, V> Double getDouble(Map<K, V> source, K key) {
        return get(source, key);
//        return getParse(source, key, Double::parseDouble);
    }

    public static <K, V> Boolean getBoolean(Map<K, V> source, K key) {
        return get(source, key);
//        return getParse(source, key, Boolean::getBoolean);
    }

    public static <K, V, T> T get(Map<K, V> source, K key, T defaultValue) {
        if (source.containsKey(key)) {
            return (T)source.get(key);
        }
        return defaultValue;
    }





    public static <K, V, T> T parseParse(Map<K, V> source, K key, Function<String, T> parser) {
        return source.get(key) == null ? null : parser.apply((String) source.get(key));
    }

    public static <K, V> Integer parseInteger(Map<K, V> source, K key) {
//        return get(source, key);
        return parseParse(source, key, Integer::parseInt);
    }

    public static <K, V> Float parseFloat(Map<K, V> source, K key) {
//        return get(source, key);
        return parseParse(source, key, Float::parseFloat);
    }

    public static <K, V> Double parseDouble(Map<K, V> source, K key) {
//        return get(source, key);
        return parseParse(source, key, Double::parseDouble);
    }

    public static <K, V> Boolean parseBoolean(Map<K, V> source, K key) {
//        return get(source, key);
        return parseParse(source, key, Boolean::getBoolean);
    }


}
