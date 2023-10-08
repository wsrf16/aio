package com.aio.portable.swiss.suite.bean;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public abstract class GenericSugar {
    public static final List<Type> parseParameterizedTypeOfSuperClass(Class<?> child) {
        Class<?> parameterizedTypeReferenceSubclass = findTypeArgumentsReferenceSubclass(child);
        // 获取父类的泛型类 ParameterizedTypeReference<具体类型>
        Type type = parameterizedTypeReferenceSubclass.getGenericSuperclass();
        Assert.isInstanceOf(ParameterizedType.class, type, "Type must be a parameterized type");
        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//        Assert.isTrue(actualTypeArguments.length == 1, "Number of type arguments must be 1");
        return Arrays.asList(actualTypeArguments);
    }

    private static final Class<?> findTypeArgumentsReferenceSubclass(Class<?> child) {
        Class<?> parent = child.getSuperclass();
        if (Object.class == parent) {
            throw new IllegalStateException("Expected ParameterizedTypeReference superclass");
        } else {
            // 如果父类是工具类本身 就返回否则就递归 直到获取到ParameterizedTypeReference
            return child;
//            return ParameterizedTypeReference.class == parent ? child : findParameterizedTypeReferenceSubclass(parent);
        }
    }

    public static final List<Type> parseTypeArgumentsOfField(Class<?> clazz, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            List<Type> typeList = parseTypeArgumentsOfField(field);
            return typeList;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static final List<Type> parseTypeArgumentsOfField(Field field) {
        Type genericType = field.getGenericType();
        ParameterizedType type = (ParameterizedType) genericType;
        Type[] typeArguments = type.getActualTypeArguments();
        return Arrays.asList(typeArguments);
    }
}
