package com.aio.portable.swiss.data.jpa;

import com.aio.portable.swiss.bean.BeanUtils;
import com.aio.portable.swiss.data.jpa.annotation.order.Order;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PageableSort {

    public final static Sort buildSort(Class<?> clazz) {
        List<Field> fieldList = BeanUtils.Fields.getDeclaredFieldIncludeParents(clazz).stream().filter(c -> c.isAnnotationPresent(Order.class)).collect(Collectors.toList());
        fieldList.stream().sorted(Comparator.comparing((Field c) -> c.getAnnotation(Order.class).priority()));
        List<Sort.Order> orderList = fieldList.stream().map(c -> {
            Order annotation = c.getAnnotation(Order.class);
            Sort.Direction direction = annotation.direction();
            String name = annotation.targetProperty();
            int priority = annotation.priority();
            return new Sort.Order(direction, name);
        }).collect(Collectors.toList());
        Sort sort = Sort.by(orderList);

        return sort;
    }
}
