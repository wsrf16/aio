package com.aio.portable.swiss.data.jpa;

import com.aio.portable.swiss.bean.BeanWorld;
import com.aio.portable.swiss.data.jpa.annotation.IgnoreSQL;
import com.aio.portable.swiss.data.jpa.annotation.where.*;
import com.aio.portable.swiss.sugar.StringWorld;
import com.google.common.base.Function;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class SpecificationWorld {
    static class Util {
        public final static Map<String, PropertyItem> getNamePropertyItem(Object bean) {
            Class<?> clazz = bean.getClass();
            Map<String, PropertyItem> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
//                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> {
                        String name = _property.getName();
                        Object value = BeanWorld.PropertyDescriptors.getValue(bean, _property);
                        Field field = null;
                        try {
                            field = BeanWorld.Fields.getDeclaredFieldIncludeParents(clazz, name);
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

    public static class PropertyItem {
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
     * @param bean
     * @return
     */
    public final static <R> Specification<R> buildSpecification(Object bean) {
        Specification<R> specification = (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicateList = SpecificationWorld.buildPredicate(bean, root, criteriaBuilder);
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
     * @param bean
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public final static <R> List<Predicate> buildPredicate(Object bean, Root<R> root, CriteriaBuilder criteriaBuilder) {
        Map<String, PropertyItem> properties = Util.getNamePropertyItem(bean);
        List<Predicate> predicateList = ((Supplier<ArrayList>)ArrayList::new).get();
        fillPredicateWithAllCriteria(properties, predicateList, root, criteriaBuilder);
        return predicateList;
    }


    /**
     * fillPredicateWithAllCriteria
     * @param properties
     * @param predicateList
     * @param root
     * @param criteriaBuilder
     */
    public final static <R> void fillPredicateWithAllCriteria(Map<String, PropertyItem> properties, List<Predicate> predicateList, Root<R> root, CriteriaBuilder criteriaBuilder) {
        properties.entrySet().stream().forEach(c -> {
            PropertyItem property = c.getValue();
            String name = property.getName();
            Object value = property.getValue();
            if (value != null) {
                if (value instanceof String) {
                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, String.class, property, predicateList);
//                    predicateList.add(criteriaBuilder.like(root.get(name).as(String.class), (String) value));
                } else if (value instanceof Boolean) {
                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, Boolean.class, property, predicateList);
//                    predicateList.add(criteriaBuilder.equal(root.get(name).as(Boolean.class), (Boolean) value));
                } else if (value instanceof Character) {
                    CriteriaUtil.LevelOne.fillPredicateWithEqualsCriteria(criteriaBuilder, root, Character.class, property, predicateList);
//                    predicateList.add(criteriaBuilder.equal(root.get(name).as(Character.class), (Character) value));
                } else if (value instanceof Integer) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Integer.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Integer) value, predicateList);
                } else if (value instanceof Long) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Long.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Long) value, predicateList);
                } else if (value instanceof Date) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Date.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Date) value, predicateList);
                } else if (value instanceof BigDecimal) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, BigDecimal.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (BigDecimal) value, predicateList);
                } else if (value instanceof Double) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Double.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Double) value, predicateList);
                } else if (value instanceof Float) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Float.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Float) value, predicateList);
                } else if (value instanceof Byte) {
                    CriteriaUtil.LevelOne.fillPredicateWithComparableCriteria(criteriaBuilder, root, Byte.class, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Byte) value, predicateList);
                } else if (value instanceof Collection) {
                    CriteriaUtil.LevelOne.fillPredicateWithInsCriteria(criteriaBuilder, root, property, predicateList);
//                    fillPredicateWithComparableCriteria(root, criteriaBuilder, name, (Byte) value, predicateList);
                }

            }
        });
    }

    static class CriteriaUtil {
        static class LevelThree {
//            public final static <T> void fillPredicate(BiFunction<Expression<T>, T, Predicate> biFunction, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
//                String name = property.getName();
//                Object value = property.getValue();
//                predicateList.add(biFunction.apply(root.get(name).as(clazz), (T) value));
////        predicateList.add(criteriaBuilder.like(root.get(name).as(clazz), (String)value));
//            }

            public final static <T> void fillPredicate(BiFunction<Expression<T>, T, Predicate> biFunction, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                Object value = property.getValue();
                predicateList.add(biFunction.apply(root.get(name).as(clazz), (T) value));
            }

            public final static <T> void fillPredicate(Function<Expression<T>, CriteriaBuilder.In<T>> biFunction, Root<?> root, String name, PropertyItem property, List<Predicate> predicateList) {
                Collection<T> value = (Collection<T>) property.getValue();
                CriteriaBuilder.In<T> predicate = biFunction.apply(root.get(name));
                value.stream().forEach(c -> predicate.value(c));
                predicateList.add(predicate);
            }
        }

        static class LevelTwo {
            public final static <T> void fillPredicateWithInCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::in, root, name, property, predicateList);
            }


            public final static void fillPredicateWithLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::like, root, name, clazz, property, predicateList);
            }


            public final static void fillPredicateWithNotLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::notLike, root, name, clazz, property, predicateList);
            }


            public final static <T> void fillPredicateWithEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::equal, root, name, clazz, property, predicateList);
            }


            public final static <T> void fillPredicateWithNotEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::notEqual, root, name, clazz, property, predicateList);
            }








            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::greaterThan, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::greaterThanOrEqualTo, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::lessThan, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicate(criteriaBuilder::lessThanOrEqualTo, root, name, clazz, property, predicateList);
            }
        }

        static class LevelOne {

            /**
             * fillPredicateWithLikesCriteria
             * @param criteriaBuilder
             * @param root
             * @param clazz
             * @param property
             * @param predicateList
             * @param <T>
             */
            public final static <T> void fillPredicateWithLikesCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                Field field = property.getField();
                String name = property.getName();
                if (field.isAnnotationPresent(IgnoreSQL.class))
                    return;
                if (field.isAnnotationPresent(Like.class)) {
                    Like annotation = field.getDeclaredAnnotation(Like.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithLikeCriteria(criteriaBuilder, root, name, (Class<String>) clazz, property, predicateList);
                } else if (field.isAnnotationPresent(NotLike.class)) {
                    NotLike annotation = field.getDeclaredAnnotation(NotLike.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithNotLikeCriteria(criteriaBuilder, root, name, (Class<String>) clazz, property, predicateList);
                }


                else if (!field.isAnnotationPresent(Like.class) && name.endsWith("Like")) {
                    name = StringWorld.removeEnd(name, "Like");
                    LevelTwo.fillPredicateWithLikeCriteria(criteriaBuilder, root, name, (Class<String>) clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(NotLike.class) && name.endsWith("NotLike")) {
                    name = StringWorld.removeEnd(name, "NotLike");
                    LevelTwo.fillPredicateWithNotLikeCriteria(criteriaBuilder, root, name, (Class<String>) clazz, property, predicateList);
                }


                else {
                    LevelTwo.fillPredicateWithLikeCriteria(criteriaBuilder, root, name, (Class<String>) clazz, property, predicateList);
                }
            }

            /**
             * fillPredicateWithEqualsCriteria
             * @param criteriaBuilder
             * @param root
             * @param clazz
             * @param property
             * @param predicateList
             * @param <T>
             */
            public final static <T extends Comparable<? super T>> void fillPredicateWithEqualsCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                Field field = property.getField();
                String name = property.getName();
                if (field.isAnnotationPresent(IgnoreSQL.class))
                    return;
                if (field.isAnnotationPresent(Equal.class)) {
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(NotEqual.class)) {
                    LevelTwo.fillPredicateWithNotEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }


                else if (!field.isAnnotationPresent(Equal.class) && name.endsWith("Equal")) {
                    name = StringWorld.removeEnd(name, "Equal");
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(NotEqual.class) && name.endsWith("NotEqual")) {
                    name = StringWorld.removeEnd(name, "NotEqual");
                    LevelTwo.fillPredicateWithNotEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }

                else {
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                    }

            }


            /**
             * fillPredicateWithInsCriteria
             * @param criteriaBuilder
             * @param root
             * @param clazz
             * @param property
             * @param predicateList
             * @param <T>
             */
            public final static <T> void fillPredicateWithInsCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, PropertyItem property, List<Predicate> predicateList) {
                Field field = property.getField();
                String name = property.getName();
                if (field.isAnnotationPresent(IgnoreSQL.class))
                    return;
                if (field.isAnnotationPresent(In.class)) {
                    LevelTwo.fillPredicateWithInCriteria(criteriaBuilder, root, name, property, predicateList);
                }


                else if (!field.isAnnotationPresent(In.class) && name.endsWith("In")) {
                    name = StringWorld.removeEnd(name, "In");
                    LevelTwo.fillPredicateWithInCriteria(criteriaBuilder, root, name, property, predicateList);
                }
            }


            /**
             * fillPredicateWithComparableCriteria
             *
             * @param criteriaBuilder
             * @param root
             * @param clazz
             * @param property
             * @param predicateList
             * @param <T>
             */
            public final static <T extends Comparable<? super T>> void fillPredicateWithComparableCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                Field field = property.getField();
                String name = property.getName();
                if (field.isAnnotationPresent(IgnoreSQL.class))
                    return;
                if (field.isAnnotationPresent(GreaterThan.class)) {
                    GreaterThan annotation = field.getDeclaredAnnotation(GreaterThan.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithGreaterThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(GreaterThanOrEqualTo.class)) {
                    GreaterThanOrEqualTo annotation = field.getDeclaredAnnotation(GreaterThanOrEqualTo.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithGreaterThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(LessThan.class)) {
                    LessThan annotation = field.getDeclaredAnnotation(LessThan.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithLessThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(LessThanOrEqualTo.class)) {
                    LessThanOrEqualTo annotation = field.getDeclaredAnnotation(LessThanOrEqualTo.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithLessThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(Equal.class)) {
                    Equal annotation = field.getDeclaredAnnotation(Equal.class);
                    name = annotation.targetProperty();
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }




                else if (!field.isAnnotationPresent(GreaterThan.class) && name.endsWith("From")) {
                    name = StringWorld.removeEnd(name, "From");
                    LevelTwo.fillPredicateWithGreaterThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(GreaterThanOrEqualTo.class) && name.endsWith("FromContain")) {
                    name = StringWorld.removeEnd(name, "FromContain");
                    LevelTwo.fillPredicateWithGreaterThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(LessThan.class) && name.endsWith("To")) {
                    name = StringWorld.removeEnd(name, "To");
                    LevelTwo.fillPredicateWithLessThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if ((!field.isAnnotationPresent(LessThanOrEqualTo.class) && name.endsWith("ToContain"))) {
                    name = StringWorld.removeEnd(name, "ToContain");
                    LevelTwo.fillPredicateWithLessThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }

                else {
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }
            }
        }
    }


    /**
     * fillPredicateWithComparableCriteria
     * @param root
     * @param criteriaBuilder
     * @param name
     * @param value
     * @param predicateList
     * @param <T>
     */
    private final static <T extends Comparable<? super T>> void fillPredicateWithComparableCriteria(Root<?> root, CriteriaBuilder criteriaBuilder, String name, T value, List<Predicate> predicateList) {
        Class<T> clazz = (Class<T>) value.getClass();
        if (name.endsWith("From")) {
            predicateList.add(criteriaBuilder.greaterThan(root.get(name).as(clazz), value));
        } else if (name.endsWith("FromContain")) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get(name).as(clazz), value));
        } else if (name.endsWith("To")) {
            predicateList.add(criteriaBuilder.lessThan(root.get(name).as(clazz), value));
        } else if (name.endsWith("ToContain")) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get(name).as(clazz), value));
        } else {
            predicateList.add(criteriaBuilder.equal(root.get(name).as(clazz), value));
        }
    }
}
