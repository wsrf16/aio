package com.aio.portable.swiss.suite.storage.rds.jpa;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.storage.rds.jpa.annotation.IgnoreSQL;
import com.aio.portable.swiss.suite.storage.rds.jpa.annotation.order.OrderBy;
import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.storage.rds.jpa.annotation.where.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class JPASugar {
    private static class Util {
        public final static Map<String, PropertyItem> getNamePropertyItem(Object bean) {
            Class<?> clazz = bean.getClass();
            Map<String, PropertyItem> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
//                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> {
                        String name = _property.getName();
                        Object value = BeanSugar.PropertyDescriptors.getValue(bean, _property);
                        Field field = null;
                        try {
                            field = BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz, name);
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                        PropertyItem propertyItem = new PropertyItem();
                        propertyItem.setName(name);
                        propertyItem.setValue(value);
                        propertyItem.setField(field);
                        propertyItem.setPropertyDescriptor(_property);
                        _map.put(name, propertyItem);
                    }, HashMap::putAll);
            return map;
        }

    }

    private static class PropertyItem {
        private String name;
        private Object value;
        private PropertyDescriptor propertyDescriptor;
        private Field field;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public PropertyDescriptor getPropertyDescriptor() {
            return propertyDescriptor;
        }

        public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
            this.propertyDescriptor = propertyDescriptor;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }
    }


    /**
     * buildSpecification
     *
     * @param bean
     * @return
     */
    public final static <R> Specification<R> buildSpecification(Object bean) {
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

    /**
     * buildPredicate
     *
     * @param bean
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public final static <R> List<Predicate> buildPredicate(Object bean, Root<R> root, CriteriaBuilder criteriaBuilder) {
        Map<String, PropertyItem> properties = Util.getNamePropertyItem(bean);
        List<Predicate> predicateList = ((Supplier<ArrayList>) ArrayList::new).get();
        fillPredicateWithAllCriteria(properties, criteriaBuilder, root, predicateList);
        return predicateList;
    }

    public final static Sort buildSort(Class<?> clazz) {
        List<Field> fieldList = BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz).stream().filter(c -> c.isAnnotationPresent(OrderBy.class)).collect(Collectors.toList());
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


    /**
     * fillPredicateWithAllCriteria
     *
     * @param properties
     * @param criteriaBuilder
     * @param root
     * @param predicateList
     */
    private final static void fillPredicateWithAllCriteria(Map<String, PropertyItem> properties, CriteriaBuilder criteriaBuilder, Root<?> root, List<Predicate> predicateList) {
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
                } else if (field.isAnnotationPresent(GreaterThan.class) || name.endsWith("GreaterThan")
                        || field.isAnnotationPresent(GreaterThanEqual.class) || name.endsWith("GreaterThanEqual")
                        || field.isAnnotationPresent(LessThan.class) || name.endsWith("LessThan")
                        || field.isAnnotationPresent(LessThanEqual.class) || name.endsWith("LessThanEqual")
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


    private abstract static class CriteriaUtil {
        public final static <T> void fillPredicate(Function<Expression<T>, CriteriaBuilder.In<T>> biFunction, Root<?> root, String name, PropertyItem property, List<Predicate> predicateList) {
            if (property.getValue() instanceof Collection<?>) {
                Collection<T> value = (Collection<T>) property.getValue();
                if (!CollectionSugar.isEmpty(value)) {
                    CriteriaBuilder.In<T> predicate = biFunction.apply(root.get(name));
                    value.stream().forEach(c -> predicate.value(c));
                    predicateList.add(predicate);
                }
            } else if (property.getValue() instanceof Object[]) {
                T[] value = (T[]) property.getValue();
                if (!CollectionSugar.isEmpty(value)) {
                    CriteriaBuilder.In<T> predicate = biFunction.apply(root.get(name));
                    Arrays.asList(value).stream().forEach(c -> predicate.value(c));
                    predicateList.add(predicate);
                }
            }
        }

        /**
         * fillPredicateWithLikesCriteria
         *
         * @param criteriaBuilder
         * @param root
         * @param property
         * @param predicateList
         * @param <T>
         */
        public final static <T> void fillPredicateWithLikesCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
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
                    Like annotation = field.getDeclaredAnnotation(Like.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("Like")) {
                    fixName = StringSugar.removeEnd(name, "Like");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.like(root.get(fixName), value));
            } else if (field.isAnnotationPresent(NotLike.class)
                    || name.endsWith("NotLike")
            ) {
                if (field.isAnnotationPresent(NotLike.class)) {
                    NotLike annotation = field.getDeclaredAnnotation(NotLike.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("NotLike")) {
                    fixName = StringSugar.removeEnd(name, "NotLike");
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
        public final static <T extends Comparable<? super T>> void fillPredicateWithEqualsCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
            Field field = property.getField();
            String name = property.getName();
            T value = (T) property.getValue();
            String fixName;
            if (field.isAnnotationPresent(IgnoreSQL.class)) {
                return;
            } else if (field.isAnnotationPresent(Equal.class)
                    || name.endsWith("Equal")
            ) {
                if (field.isAnnotationPresent(Equal.class)) {
                    Equal annotation = field.getDeclaredAnnotation(Equal.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("Equal")) {
                    fixName = StringSugar.removeEnd(name, "Equal");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.equal(root.get(fixName), value));
            } else if (field.isAnnotationPresent(NotEqual.class)
                    || name.endsWith("NotEqual")
            ) {
                if (field.isAnnotationPresent(NotEqual.class)) {
                    NotEqual annotation = field.getDeclaredAnnotation(NotEqual.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("NotEqual")) {
                    fixName = StringSugar.removeEnd(name, "NotEqual");
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
        public final static <T> void fillPredicateWithInsCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
            Field field = property.getField();
            String name = property.getName();
            String fixName;
            if (field.isAnnotationPresent(IgnoreSQL.class)) {
                return;
            } else if (field.isAnnotationPresent(In.class)
                    || name.endsWith("In")
            ) {
                if (field.isAnnotationPresent(In.class)) {
                    In annotation = field.getDeclaredAnnotation(In.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("In")) {
                    fixName = StringSugar.removeEnd(name, "In");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                CriteriaUtil.fillPredicate(criteriaBuilder::in, root, fixName, property, predicateList);
            }
        }


        /**
         * fillPredicateWithComparableCriteria
         *
         * @param criteriaBuilder
         * @param root
         * @param property
         * @param predicateList
         * @param <T>
         */
        public final static <T extends Comparable<? super T>> void fillPredicateWithComparableCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
            Field field = property.getField();
            String name = property.getName();
            T value = (T) property.getValue();
            String fixName;
            if (field.isAnnotationPresent(IgnoreSQL.class)) {
                return;
            } else if (field.isAnnotationPresent(GreaterThan.class)
                    || name.endsWith("GreaterThan")
            ) {
                if (field.isAnnotationPresent(GreaterThan.class)) {
                    GreaterThan annotation = field.getDeclaredAnnotation(GreaterThan.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("GreaterThan")) {
                    fixName = StringSugar.removeEnd(name, "GreaterThan");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.greaterThan(root.get(fixName), value));
            } else if (field.isAnnotationPresent(GreaterThanEqual.class)
                    || name.endsWith("GreaterThanEqual")
            ) {
                if (field.isAnnotationPresent(GreaterThanEqual.class)) {
                    GreaterThanEqual annotation = field.getDeclaredAnnotation(GreaterThanEqual.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("GreaterThanEqual")) {
                    fixName = StringSugar.removeEnd(name, "GreaterThanEqual");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(fixName), value));
            } else if (field.isAnnotationPresent(LessThan.class)
                    || name.endsWith("LessThan")
            ) {
                if (field.isAnnotationPresent(LessThan.class)) {
                    LessThan annotation = field.getDeclaredAnnotation(LessThan.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("LessThan")) {
                    fixName = StringSugar.removeEnd(name, "LessThan");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.lessThan(root.get(fixName), value));
            } else if (field.isAnnotationPresent(LessThanEqual.class)
                    || name.endsWith("LessThanEqual")
            ) {
                if (field.isAnnotationPresent(LessThanEqual.class)) {
                    LessThanEqual annotation = field.getDeclaredAnnotation(LessThanEqual.class);
                    fixName = annotation.targetProperty();
                } else if (name.endsWith("LessThanEqual")) {
                    fixName = StringSugar.removeEnd(name, "LessThanEqual");
                } else {
                    throw new RuntimeException(name + " is illegal.");
                }
                predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(fixName), value));
            }
        }
    }


}
