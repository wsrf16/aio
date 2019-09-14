package com.aio.portable.swiss.data.jpa;

import com.aio.portable.swiss.data.jpa.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
//import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class SpecificationUtils {
    static class Util {
        private static Object getValue(Object bean, PropertyDescriptor c) {
            try {
                return c.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static Map<String, PropertyItem> getNamePropertyItem(Object bean) {
            Class<?> clazz = bean.getClass();
            Map<String, PropertyItem> map = Arrays.stream(BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
//                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> {
                        String name = _property.getName();
                        Object value = getValue(bean, _property);
                        Field field = getDeclaredField(clazz, name);
                        PropertyItem propertyItem = new PropertyItem();
                        propertyItem.setName(name);
                        propertyItem.setValue(value);
                        propertyItem.setField(field);
                        propertyItem.setPropertyDescriptor(_property);
                        _map.put(name, propertyItem);
                    }, HashMap::putAll);
            return map;
        }

        private final static Field getDeclaredField(Class<?> clazz, String name) {
            try {
                List<String> fieldNames = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().map(Field::getName).collect(Collectors.toList());
                Field field;
                if (fieldNames.contains(name)) {
                    field = clazz.getDeclaredField(name);
                } else {
                    Class<?> parentClazz = clazz.getSuperclass();
                    if (parentClazz == null) {
                        throw new NoSuchFieldException(name);
                    } else {
                        field = getDeclaredField(parentClazz, name);
                    }
                }
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
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
        Specification<R> specification = (root, query, criteriaBuilder) -> {
            Map<String, PropertyItem> properties = Util.getNamePropertyItem(bean);
            List<Predicate> predicateList = SpecificationUtils.buildPredicate(properties, root, criteriaBuilder);
            Predicate predicate = criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            return predicate;
        };
        return specification;
    }

    /**
     * buildPredicate
     * @param properties
     * @param root
     * @param criteriaBuilder
     * @return
     */
    public final static <R> List<Predicate> buildPredicate(Map<String, PropertyItem> properties, Root<R> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();
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
                    CriteriaUtil.LevelOne.fillPredicateWithLikesCriteria(criteriaBuilder, root, String.class, property, predicateList);
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
                }
            }
        });
    }

    static class CriteriaUtil {
        static class LevelThree {
            public final static <T> void fillPredicateWithXxxCriteria(BiFunction<Expression<T>, T, Predicate> biFunction, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                String name = property.getName();
                Object value = property.getValue();
                predicateList.add(biFunction.apply(root.get(name).as(clazz), (T) value));
//        predicateList.add(criteriaBuilder.like(root.get(name).as(clazz), (String)value));
            }

            public final static <T> void fillPredicateWithXxxCriteria(BiFunction<Expression<T>, T, Predicate> biFunction, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                Object value = property.getValue();
                predicateList.add(biFunction.apply(root.get(name).as(clazz), (T) value));
//        predicateList.add(criteriaBuilder.like(root.get(name).as(clazz), (String)value));
            }
        }

        static class LevelTwo {
            public final static void fillPredicateWithLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::like, root, clazz, property, predicateList);
            }

            public final static void fillPredicateWithNotLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::notLike, root, clazz, property, predicateList);
            }

            public final static <T> void fillPredicateWithEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::equal, root, clazz, property, predicateList);
            }

            public final static <T> void fillPredicateWithNotEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::notEqual, root, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::greaterThan, root, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::greaterThanOrEqualTo, root, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::lessThan, root, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::lessThanOrEqualTo, root, clazz, property, predicateList);
            }






            public final static void fillPredicateWithLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name,Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::equal, root, name, clazz, property, predicateList);
            }

            public final static void fillPredicateWithNotLikeCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name,Class<String> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::notEqual, root, name, clazz, property, predicateList);
            }

            public final static <T> void fillPredicateWithEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::equal, root, name, clazz, property, predicateList);
            }

            public final static <T> void fillPredicateWithNotEqualCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::notEqual, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::greaterThan, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithGreaterThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::greaterThanOrEqualTo, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::lessThan, root, name, clazz, property, predicateList);
            }

            public final static <T extends Comparable<? super T>> void fillPredicateWithLessThanOrEqualToCriteria(CriteriaBuilder criteriaBuilder, Root<?> root, String name, Class<T> clazz, PropertyItem property, List<Predicate> predicateList) {
                LevelThree.fillPredicateWithXxxCriteria(criteriaBuilder::lessThanOrEqualTo, root, name, clazz, property, predicateList);
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
                    LevelTwo.fillPredicateWithLikeCriteria(criteriaBuilder, root, (Class<String>) clazz, property, predicateList);
                } else if (field.isAnnotationPresent(NotLike.class)) {
                    LevelTwo.fillPredicateWithNotLikeCriteria(criteriaBuilder, root, (Class<String>) clazz, property, predicateList);
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
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(NotEqual.class)) {
                    LevelTwo.fillPredicateWithNotEqualCriteria(criteriaBuilder, root, clazz, property, predicateList);
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
                    LevelTwo.fillPredicateWithGreaterThanCriteria(criteriaBuilder, root, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(GreaterThanOrEqualTo.class)) {
                    LevelTwo.fillPredicateWithGreaterThanOrEqualToCriteria(criteriaBuilder, root, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(LessThan.class)) {
                    LevelTwo.fillPredicateWithLessThanCriteria(criteriaBuilder, root, clazz, property, predicateList);
                } else if (field.isAnnotationPresent(LessThanOrEqualTo.class)) {
                    LevelTwo.fillPredicateWithLessThanOrEqualToCriteria(criteriaBuilder, root, clazz, property, predicateList);
                }




                else if (!field.isAnnotationPresent(GreaterThan.class) && name.endsWith("From")) {
                    name = removeEnd(name, "From");
                    LevelTwo.fillPredicateWithGreaterThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(GreaterThanOrEqualTo.class) && name.endsWith("FromContain")) {
                    name = removeEnd(name, "FromContain");
                    LevelTwo.fillPredicateWithGreaterThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if (!field.isAnnotationPresent(LessThan.class) && name.endsWith("To")) {
                    name = removeEnd(name, "To");
                    LevelTwo.fillPredicateWithLessThanCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                } else if ((!field.isAnnotationPresent(LessThanOrEqualTo.class) && name.endsWith("ToContain"))) {
                    name = removeEnd(name, "ToContain");
                    LevelTwo.fillPredicateWithLessThanOrEqualToCriteria(criteriaBuilder, root, name, clazz, property, predicateList);
                }

                else {
                    LevelTwo.fillPredicateWithEqualCriteria(criteriaBuilder, root, clazz, property, predicateList);
                }
            }

            private static String removeEnd(String str, String remove) {
                if (StringUtils.hasLength(str) && StringUtils.hasLength(remove)) {
                    return str.endsWith(remove) ? str.substring(0, str.length() - remove.length()) : str;
                } else {
                    return str;
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
