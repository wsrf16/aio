package com.aio.portable.swiss.sugar.type;

import java.util.Map;
import java.util.function.Function;

public abstract class MapSugar {
    public static <K, V, T> T get(Map<K, V> source, K key) {
        return (T)source.get(key);
    }

    public static <K, V, T> T getParse(Map<K, V> source, K key, Function<String, T> parser) {
        return parser.apply((String) source.get(key));
    }

    public static <K, V> int getInteger(Map<K, V> source, K key) {
        return getParse(source, key, Integer::parseInt);
    }

    public static <K, V> float getFloat(Map<K, V> source, K key) {
        return getParse(source, key, Float::parseFloat);
    }

    public static <K, V> double getDouble(Map<K, V> source, K key) {
        return getParse(source, key, Double::parseDouble);
    }

//    public static <K, V> double getBoolen(Map<K, V> source, K key) {
//        return getParse(source, key, Boolean::getBoolean);
//    }



    public static <K, V, T> T get(Map<K, V> source, K key, T defaultValue) {
        if (source.containsKey(key)) {
            return (T)source.get(key);
        }
        return defaultValue;
    }
}
