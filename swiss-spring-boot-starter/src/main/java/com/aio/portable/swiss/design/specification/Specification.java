package com.aio.portable.swiss.design.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public abstract class Specification {
    public static <T> Boolean allMatch(T t, Collection<Predicate<T>> predicates) {
        return t == null ? null : predicates.stream().allMatch(c -> c.test(t));
    }

    public static <T> Boolean anyMatch(T t, Collection<Predicate<T>> predicates) {
        return t == null ? null : predicates.stream().anyMatch(c -> c.test(t));
    }

    public static <T> List<T> filter(Collection<T> col, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : col) {
            if (t != null && predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    public static <T> List<T> filterAllMatch(Collection<T> col, Collection<Predicate<T>> predicateCollection) {
        List<T> result = new ArrayList<>();
        for (T t : col) {
            if (t != null) {
                Boolean test = allMatch(t, predicateCollection) ? true : false;
                if (test)
                    result.add(t);
            }
        }
        return result;
    }

    public static <T> List<T> filterAnyMatch(Collection<T> col, Collection<Predicate<T>> predicateCollection) {
        List<T> result = new ArrayList<>();
        for (T t : col) {
            if (t != null) {
                Boolean test = anyMatch(t, predicateCollection) ? true : false;
                if (test)
                    result.add(t);
            }
        }
        return result;
    }


}

