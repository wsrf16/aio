package com.aio.portable.swiss.suite.storage.db.jpa;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.IgnoreSQL;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class CriteriaUtil {
    /**
     * fillPredicateWithLikesCriteria
     *
     * @param criteriaBuilder
     * @param root
     * @param property
     * @param predicateList
     * @param <T>
     */
    final static <T> void fillPredicateWithLikesCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
        Field field = property.getField();
        String name = property.getName();
        String value = (String) property.getValue();
        String fixName;
        if (field.isAnnotationPresent(IgnoreSQL.class)) {
            return;
        } else if (field.isAnnotationPresent(Like.class)
                || name.endsWith("Like")
        ) {
            if (field.isAnnotationPresent(Like.class)) {
                fixName = field.getDeclaredAnnotation(Like.class).targetProperty();
            } else if (name.endsWith("Like")) {
                fixName = StringSugar.trimEnd(name, "Like");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.like(root.get(fixName), value));
        } else if (field.isAnnotationPresent(NotLike.class)
                || name.endsWith("NotLike")
        ) {
            if (field.isAnnotationPresent(NotLike.class)) {
                fixName = field.getDeclaredAnnotation(NotLike.class).targetProperty();
            } else if (name.endsWith("NotLike")) {
                fixName = StringSugar.trimEnd(name, "NotLike");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.notLike(root.get(fixName), value));
        }
    }

    /**
     * fillPredicateWithEqualsCriteria
     *
     * @param criteriaBuilder
     * @param root
     * @param property
     * @param predicateList
     * @param <T>
     */
    final static <T extends Comparable<? super T>> void fillPredicateWithEqualsCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
        Field field = property.getField();
        String name = property.getName();
        T value = (T) property.getValue();
        String fixName;
        if (field.isAnnotationPresent(IgnoreSQL.class)) {
            return;
        } else if (field.isAnnotationPresent(Equal.class)
                || name.endsWith("Equal")) {
            if (field.isAnnotationPresent(Equal.class)) {
                fixName = field.getDeclaredAnnotation(Equal.class).targetProperty();
            } else if (name.endsWith("Equal")) {
                fixName = StringSugar.trimEnd(name, "Equal");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.equal(root.get(fixName), value));
        } else if (field.isAnnotationPresent(NotEqual.class)
                || name.endsWith("NotEqual")) {
            if (field.isAnnotationPresent(NotEqual.class)) {
                fixName = field.getDeclaredAnnotation(NotEqual.class).targetProperty();
            } else if (name.endsWith("NotEqual")) {
                fixName = StringSugar.trimEnd(name, "NotEqual");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.notEqual(root.get(fixName), value));
        } else {
            fixName = name;
            predicateList.add(criteriaBuilder.equal(root.get(fixName), value));
        }
    }


    /**
     * fillPredicateWithInsCriteria
     *
     * @param criteriaBuilder
     * @param root
     * @param property
     * @param predicateList
     * @param <T>
     */
    final static <T> void fillPredicateWithInsCriteria(CriteriaBuilder criteriaBuilder, Root<T> root, PropertyItem property, List<Predicate> predicateList) {
        Field field = property.getField();
        String name = property.getName();
        String fixName;
        if (field.isAnnotationPresent(IgnoreSQL.class)) {
            return;
        } else if (field.isAnnotationPresent(In.class)
                || name.endsWith("In")
        ) {
            if (field.isAnnotationPresent(In.class)) {
                fixName = field.getDeclaredAnnotation(In.class).targetProperty();
            } else if (name.endsWith("In")) {
                fixName = StringSugar.trimEnd(name, "In");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
//            CriteriaUtil.fillPredicate(criteriaBuilder::in, root, fixName, property, predicateList);

            if (property.getValue() instanceof Collection<?>) {
                Collection<T> value = (Collection<T>) property.getValue();
                if (!CollectionSugar.isEmpty(value)) {
                    CriteriaBuilder.In<T> predicate = criteriaBuilder.in(root.get(fixName));
                    value.stream().forEach(c -> predicate.value(c));
                    predicateList.add(predicate);
                }
            } else if (property.getValue() instanceof Object[]) {
                T[] value = (T[]) property.getValue();
                if (!CollectionSugar.isEmpty(value)) {
                    CriteriaBuilder.In<T> predicate = criteriaBuilder.in(root.get(fixName));
                    Arrays.asList(value).stream().forEach(c -> predicate.value(c));
                    predicateList.add(predicate);
                }
            }
        }
    }



    /**
     * fillPredicateWithBetweenCriteria
     *
     * @param criteriaBuilder
     * @param root
     * @param property
     * @param predicateList
     * @param <T>
     */
    final static <T extends Comparable<? super T>> void fillPredicateWithBetweenCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
        Field field = property.getField();
        String name = property.getName();
        String fixName;
        if (field.isAnnotationPresent(IgnoreSQL.class)) {
            return;
        } else if (field.isAnnotationPresent(Between.class)
                || name.endsWith("Between")
        ) {
            if (field.isAnnotationPresent(Between.class)) {
                fixName = field.getDeclaredAnnotation(Between.class).targetProperty();
            } else if (name.endsWith("Between")) {
                fixName = StringSugar.trimEnd(name, "Between");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
//            CriteriaUtil.fillPredicate(criteriaBuilder::in, root, fixName, property, predicateList);

            if (property.getValue() instanceof Collection<?>) {
                Collection<T> value = (Collection<T>) property.getValue();
                if (!CollectionSugar.isEmpty(value)) {
                    List<T> collect = value.stream().collect(Collectors.toList());

                    if (collect.size() < 2)
                        throw new RuntimeException(name + ": the size must be more than 2");

                    Predicate predicate = criteriaBuilder.between(root.get(fixName), collect.get(0), collect.get(1));
                    predicateList.add(predicate);
                }
            } else if (property.getValue() instanceof Object[]) {
                T[] values = (T[]) property.getValue();

                if (values.length < 2)
                    throw new RuntimeException(name + ": the size must be more than 2");

                Predicate predicate = criteriaBuilder.between(root.get(fixName), values[0], values[1]);
                predicateList.add(predicate);
            }
        }
    }

//    private final static <T> void fillPredicate(Function<Expression<T>, CriteriaBuilder.In<T>> biFunction, Root<?> root, String name, PropertyItem property, List<Predicate> predicateList) {
//        if (property.getValue() instanceof Collection<?>) {
//            Collection<T> value = (Collection<T>) property.getValue();
//            if (!CollectionSugar.isEmpty(value)) {
//                CriteriaBuilder.In<T> predicate = biFunction.apply(root.get(name));
//                value.stream().forEach(c -> predicate.value(c));
//                predicateList.add(predicate);
//            }
//        } else if (property.getValue() instanceof Object[]) {
//            T[] value = (T[]) property.getValue();
//            if (!CollectionSugar.isEmpty(value)) {
//                CriteriaBuilder.In<T> predicate = biFunction.apply(root.get(name));
//                Arrays.asList(value).stream().forEach(c -> predicate.value(c));
//                predicateList.add(predicate);
//            }
//        }
//    }


    /**
     * fillPredicateWithComparableCriteria
     *
     * @param criteriaBuilder
     * @param root
     * @param property
     * @param predicateList
     * @param <T>
     */
    final static <T extends Comparable<? super T>> void fillPredicateWithComparableCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
        Field field = property.getField();
        String name = property.getName();
        T value = (T) property.getValue();
        String fixName;
        if (field.isAnnotationPresent(IgnoreSQL.class)) {
            return;
        } else if (field.isAnnotationPresent(GreaterThan.class)
                || name.endsWith("GreaterThan")) {
            if (field.isAnnotationPresent(GreaterThan.class)) {
                fixName = field.getDeclaredAnnotation(GreaterThan.class).targetProperty();
            } else if (name.endsWith("GreaterThan")) {
                fixName = StringSugar.trimEnd(name, "GreaterThan");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.greaterThan(root.get(fixName), value));
        } else if (field.isAnnotationPresent(GreaterThanOrEqualTo.class)
                || name.endsWith("GreaterThanOrEqualTo")) {
            if (field.isAnnotationPresent(GreaterThanOrEqualTo.class)) {
                fixName = field.getDeclaredAnnotation(GreaterThanOrEqualTo.class).targetProperty();
            } else if (name.endsWith("GreaterThanOrEqualTo")) {
                fixName = StringSugar.trimEnd(name, "GreaterThanOrEqualTo");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fixName), value));
        } else if (field.isAnnotationPresent(LessThan.class)
                || name.endsWith("LessThan")) {
            if (field.isAnnotationPresent(LessThan.class)) {
                fixName = field.getDeclaredAnnotation(LessThan.class).targetProperty();
            } else if (name.endsWith("LessThan")) {
                fixName = StringSugar.trimEnd(name, "LessThan");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.lessThan(root.get(fixName), value));
        } else if (field.isAnnotationPresent(LessThanOrEqualTo.class)
                || name.endsWith("LessThanOrEqualTo")) {
            if (field.isAnnotationPresent(LessThanOrEqualTo.class)) {
                fixName = field.getDeclaredAnnotation(LessThanOrEqualTo.class).targetProperty();
            } else if (name.endsWith("LessThanOrEqualTo")) {
                fixName = StringSugar.trimEnd(name, "LessThanOrEqualTo");
            } else {
                throw new RuntimeException(name + " is illegal.");
            }
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(fixName), value));
        }
    }
}
