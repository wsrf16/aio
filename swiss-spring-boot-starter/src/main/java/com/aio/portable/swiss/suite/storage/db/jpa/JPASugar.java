package com.aio.portable.swiss.suite.storage.db.jpa;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.IgnoreSQL;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.order.OrderBy;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.Between;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.Equal;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.GreaterThan;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.GreaterThanOrEqualTo;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.In;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.LessThan;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.LessThanOrEqualTo;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.Like;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.NotEqual;
import com.aio.portable.swiss.suite.storage.db.jpa.annotation.where.NotLike;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JPASugar {
    /**
     * buildSpecification
     *
     * @param bean
     * @return
     */
    public static final <R> Specification<R> buildSpecification(Object bean) {
        Specification<R> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = JPASugar.buildPredicate(bean, root, criteriaBuilder);
            Predicate predicate;

            if (true) {
                predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            } else {
                CriteriaQuery<Object> _criteriaQuery = criteriaBuilder.createQuery();
                Root<Object> _root = _criteriaQuery.from(Object.class);
                _criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
                predicate = _criteriaQuery.getRestriction();
            }
            return predicate;
        };
        return specification;
    }

    public static final <T, ID> T convertToSavedOne(JpaRepositoryImplementation<T, ID> specificationRepository, T t) {
        Specification<T> specification = JPASugar.<T>buildSpecification(t);
        Optional<T> optional = specificationRepository.findOne(specification);
        T target;
        if (optional.isPresent()) {
            target = optional.get();
            BeanUtils.copyProperties(t, target, ClassSugar.PropertyDescriptors.getNullPropertyNames(t));
        } else {
            target = t;
        }


        return target;
    }

    public static final <T, ID> T saveIgnoreNullProperties(JpaRepositoryImplementation<T, ID> specificationRepository, T t) {
        T target = convertToSavedOne(specificationRepository, t);
        specificationRepository.save(target);
        return target;
    }

    /**
     * buildPredicate
     *
     * @param bean
     * @param root
     * @param criteriaBuilder
     * @return
     */
    private static final <R> List<Predicate> buildPredicate(Object bean, Root<R> root, CriteriaBuilder criteriaBuilder) {
        Map<String, PropertyItem> properties = PropertyItem.getNamePropertyItemMap(bean);
        List<Predicate> predicateList = buildPredicateList(properties, root, criteriaBuilder);
        return predicateList;
    }

    public static final Sort buildSort(Class<?> clazz) {
        List<Field> fieldList = ClassSugar.Fields.getDeclaredFieldIncludeParents(clazz).stream().filter(c -> c.isAnnotationPresent(OrderBy.class)).collect(Collectors.toList());
        fieldList.stream().sorted(Comparator.comparing((Field c) -> c.getAnnotation(OrderBy.class).priority()));
        List<Sort.Order> orderList = fieldList.stream().map(c -> {
            OrderBy annotation = c.getAnnotation(OrderBy.class);
            Sort.Direction direction = annotation.direction();
            String name = annotation.targetProperty();
            int priority = annotation.priority();
            return new Sort.Order(direction, name);
        }).collect(Collectors.toList());
        Sort sort = Sort.by(orderList);

        return sort;
    }


    private static final List<Predicate> buildPredicateList(Map<String, PropertyItem> properties, Root<?> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = ((Supplier<ArrayList>) ArrayList::new).get();
        fillPredicateWithAllCriteria(properties, root, criteriaBuilder, predicateList);
        return predicateList;
    }

    /**
     * fillPredicateWithAllCriteria
     *
     * @param properties
     * @param criteriaBuilder
     * @param root
     * @param predicateList
     */
    private static final void fillPredicateWithAllCriteria(Map<String, PropertyItem> properties, Root<?> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicateList) {
        properties.entrySet().stream().forEach(c -> {
            PropertyItem property = c.getValue();
            Field field = property.getField();
            String name = property.getName();
            Object value = property.getValue();
            if (value != null) {
                if (field.isAnnotationPresent(IgnoreSQL.class)) {
                    return;
                } else if (field.isAnnotationPresent(Like.class) || name.endsWith("Like")
                        || field.isAnnotationPresent(NotLike.class) || name.endsWith("NotLike")
                ) {
                    CriteriaUtil.fillPredicateWithLikesCriteria(criteriaBuilder, root, property, predicateList);
                } else if (field.isAnnotationPresent(In.class) || name.endsWith("In")
                ) {
                    CriteriaUtil.fillPredicateWithInsCriteria(criteriaBuilder, root, property, predicateList);
                } else if (field.isAnnotationPresent(Between.class) || name.endsWith("Between")
                ) {
                    CriteriaUtil.fillPredicateWithBetweenCriteria(criteriaBuilder, root, property, predicateList);
                } else if (field.isAnnotationPresent(GreaterThan.class) || name.endsWith("GreaterThan")
                        || field.isAnnotationPresent(GreaterThanOrEqualTo.class) || name.endsWith("GreaterThanOrEqualTo")
                        || field.isAnnotationPresent(LessThan.class) || name.endsWith("LessThan")
                        || field.isAnnotationPresent(LessThanOrEqualTo.class) || name.endsWith("LessThanOrEqualTo")
                ) {
                    CriteriaUtil.fillPredicateWithComparableCriteria(criteriaBuilder, root, property, predicateList);
                } else if (field.isAnnotationPresent(Equal.class) || name.endsWith("Equal")
                        || field.isAnnotationPresent(NotEqual.class) || name.endsWith("NotEqual")
                ) {
                    CriteriaUtil.fillPredicateWithEqualsCriteria(criteriaBuilder, root, property, predicateList);
                } else {
                    CriteriaUtil.fillPredicateWithEqualsCriteria(criteriaBuilder, root, property, predicateList);
                }


            }
//            if (value != null) {
//                if (value instanceof String) {
//                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, String.class, property, predicateList);
////                    predicateList.add(criteriaBuilder.like(root.get(name).as(String.class), (String) value));
//                } else if (value instanceof Boolean) {
//                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, Boolean.class, property, predicateList);
////                    predicateList.add(criteriaBuilder.equal(root.get(name).as(Boolean.class), (Boolean) value));
//                } else if (value instanceof Character) {
//                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, Character.class, property, predicateList);
////                    predicateList.add(criteriaBuilder.equal(root.get(name).as(Character.class), (Character) value));
//                } else if (value instanceof Integer) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Integer.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Integer) value, predicateList);
//                } else if (value instanceof Long) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Long.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Long) value, predicateList);
//                } else if (value instanceof Date) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Date.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Date) value, predicateList);
//                } else if (value instanceof BigDecimal) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, BigDecimal.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (BigDecimal) value, predicateList);
//                } else if (value instanceof Double) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Double.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Double) value, predicateList);
//                } else if (value instanceof Float) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Float.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Float) value, predicateList);
//                } else if (value instanceof Byte) {
//                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Byte.class, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Byte) value, predicateList);
//                } else if (value instanceof Collection<?>) {
//                    if (!CollectionSugar.isEmpty((Collection<?>) value)) {
//                        CriteriaUtil.LevelOne.fillPredicateWithInsCriteria(criteriaBuilder, root, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Byte) value, predicateList);
//                    }
//                } else if (value instanceof Object[]) {
//                    if (!CollectionSugar.isEmpty((Object[]) value)) {
//                        CriteriaUtil.LevelOne.fillPredicateWithInsCriteria(criteriaBuilder, root, property, predicateList);
////                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Byte) value, predicateList);
//                    }
//                }
//            }

        });
    }


}
