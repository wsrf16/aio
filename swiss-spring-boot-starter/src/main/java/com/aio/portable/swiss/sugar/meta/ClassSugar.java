package com.aio.portable.swiss.sugar.meta;

import com.aio.portable.swiss.sugar.DynamicProxySugar;
import com.aio.portable.swiss.sugar.meta.function.ClassSetter;
import com.aio.portable.swiss.sugar.meta.function.InstanceSetter;
import com.aio.portable.swiss.sugar.meta.function.ClassGetter;
import com.aio.portable.swiss.sugar.meta.function.InstanceGetter;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.GenericTypeResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.StringJoiner;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class ClassSugar {

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap();

    //isPrimitiveWrapper
    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
        primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
        primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
        primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
        primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
        primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
        primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
        primitiveWrapperTypeMap.put(Void.class, Void.TYPE);

        primitiveWrapperTypeMap.put(Boolean[].class, boolean[].class);
        primitiveWrapperTypeMap.put(Byte[].class, byte[].class);
        primitiveWrapperTypeMap.put(Character[].class, char[].class);
        primitiveWrapperTypeMap.put(Double[].class, double[].class);
        primitiveWrapperTypeMap.put(Float[].class, float[].class);
        primitiveWrapperTypeMap.put(Integer[].class, int[].class);
        primitiveWrapperTypeMap.put(Long[].class, long[].class);
        primitiveWrapperTypeMap.put(Short[].class, short[].class);
    }

    public static boolean isPrimitive(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive();
    }

    // ClassUtils.isPrimitiveOrWrapper
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return primitiveWrapperTypeMap.containsKey(clazz);
    }

    public static boolean isSimilarPrimitiveOrWrapper(Class<?> clazz1, Class<?> clazz2) {
        boolean isPrimitiveOrWrapper = isPrimitiveOrWrapper(clazz1) && isPrimitiveOrWrapper(clazz2);
        if (!isPrimitiveOrWrapper)
            return false;
        Class<?> targetClazz1 = primitiveWrapperTypeMap.getOrDefault(clazz1, clazz1);
        Class<?> targetClazz2 = primitiveWrapperTypeMap.getOrDefault(clazz2, clazz2);
        return targetClazz1.equals(targetClazz2);

    }

//    /**
//     * isSimpleValueType
//     *
//     * @param clazz
//     * @return
//     */
//    public static boolean isSimpleValueType(Class<?> clazz) {
//        return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz) || Enum.class.isAssignableFrom(clazz) || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz || URL.class == clazz || Locale.class == clazz || Class.class == clazz;
//    }

    /**
     * isReference
     *
     * @param clazz
     * @return
     */
    public static boolean isReference(Class<?> clazz) {
        return Object.class.isAssignableFrom(clazz);
    }

    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return eg. file:/D:/.../all-in-one/park/target/classes/com/aio/portable/park/bean/Student.class
     */
    public static String getPath(final Class<?> clazz) {
        String clazzFile = convertClassNameToResourcePath(clazz.getTypeName());
        URL location = null;
        ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            CodeSource cs = domain.getCodeSource();
            if (cs != null)
                location = cs.getLocation();
            if (location != null) {
                if (org.springframework.util.ResourceUtils.URL_PROTOCOL_FILE.equals(location.getProtocol())) {
//                    if (location.toExternalForm().endsWith(".jar") ||
//                            location.toExternalForm().endsWith(".zip"))
//                        try {
//                            location = new URL((org.springframework.util.ResourceUtils.URL_PROTOCOL_JAR + ":").concat(location.toExternalForm())
//                                    .concat("!/").concat(clazzFile));
//                        } catch (MalformedURLException e) {
//                            throw new RuntimeException(e);
//                        }
//                    else if (new File(location.getFile()).isDirectory())
//                        try {
//                            location = new URL(location, clazzFile);
//                        } catch (MalformedURLException e) {
//                            throw new RuntimeException(e);
//                        }

                    try {
                        if (location.toExternalForm().endsWith(".jar") ||
                                location.toExternalForm().endsWith(".zip")) {
                            location = new URL((org.springframework.util.ResourceUtils.URL_PROTOCOL_JAR + ":").concat(location.toExternalForm()).concat("!/").concat(clazzFile));
                        } else if (new File(location.getFile()).isDirectory()) {
                            location = new URL(location, clazzFile);
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        if (location == null) {
            ClassLoader classLoader = clazz.getClassLoader();
            location = classLoader != null ?
                    classLoader.getResource(clazzFile) :
                    ClassLoader.getSystemResource(clazzFile);
        }
        return location.toString();
    }


    /**
     * getShortName -> org.springframework.util.ClassUtils
     *
     * @param className
     * @return
     */
    public static String getShortName(String className) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(className);
        return shortClassName;
    }

    /**
     * getClassFileName -> org.springframework.util.ClassUtils
     *
     * @param clazz
     * @return
     */
    public static String getClassFileName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getClassFileName(clazz);
        return classFileName;
    }

    /**
     * getPackageName -> org.springframework.util.ClassUtils.getPackageName
     *
     * @param clazz
     * @return
     */
    public static String getPackageName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getPackageName(clazz);
        return classFileName;
    }


    /**
     * getBeanName -> Introspector.decapitalize
     *
     * @param shortClassName
     * @return
     */
    public static String spellBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }


    public static ArrayList<Class<?>> listLoadedClasses() {
        try {
            Field field = ClassLoader.class.getDeclaredField("classes");
            field.setAccessible(true);
            Vector<Class<?>> vector = (Vector<Class<?>>) field.get(ClassLoaderSugar.getDefaultClassLoader());
            ArrayList<Class<?>> classArrayList = CollectionSugar.toList(vector.elements());
            return classArrayList;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    /**
//     * convertClassName2ResourcePath
//     *
//     * @param className className/packageName eg. com.company.biz | com.company.biz.Book
//     * @return com/company/biz | com/company/biz/Book.class
//     */
//    public static String convertClassNameToResourcePath(String className) {
//        String path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(className).concat(".class");
//        return path;
//    }

    /**
     * convertClassNameToPath
     *
     * @param className com.company.biz | com.company.biz.Book
     * @return className/packageName eg. com/company/biz | com/company/biz/Book.class
     */
    public static String convertClassNameToResourcePath(String className) {
        String temp = className.replace(".", "/");
        String lastWord = StringSugar.getLastWord(temp, "/");
        temp = StringSugar.isCapitalize(lastWord) ? temp + ".class" : temp;
        return temp;
    }

    /**
     * convertPathToClassName
     *
     * @param path className/packageName eg. com/company/biz | com/company/biz/Book.class
     * @return com.company.biz | com.company.biz.Book
     */
    public static String convertResourcePathToClassName(String path) {
        return ResourceSugar.convertResourcePathToClassName(path);
    }

    /**
     * getInnerClass
     * @param clazz
     * @param name
     * @return
     */
    public static Class<?> getInnerClass(Class<?> clazz, String name) {
        return ClassLoaderSugar.forName(MessageFormat.format("{0}${1}", clazz.toString(), name));
    }

    /**
     * getConstructor
     *
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     */
    private static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            Constructor<T> constructor = clazz.getConstructor(parameterTypes);
            ReflectionUtils.makeAccessible(constructor);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getDeclaredConstructor
     *
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     */
    private static <T> Constructor<T> getDeclaredConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            Constructor<T> declaredConstructor = parameterTypes == null ? clazz.getDeclaredConstructor() : clazz.getDeclaredConstructor(parameterTypes);
            ReflectionUtils.makeAccessible(declaredConstructor);
            return declaredConstructor;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * newInstance
     *
     * @param clazz
     * @param parameterTypes
     * @param initargs
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz,
                                          Class<?>[] parameterTypes,
                                          Object[] initargs) {
        try {
            return getConstructor(clazz, parameterTypes).newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * newDeclaredInstance
     *
     * @param clazz
     * @param parameterTypes
     * @param initargs
     * @param <T>
     * @return
     */
    public static <T> T newDeclaredInstance(Class<T> clazz,
                                                  Class<?>[] parameterTypes,
                                                  Object[] initargs) {
        try {
            return getDeclaredConstructor(clazz, parameterTypes).newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * newInstance
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return getConstructor(clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * newInstance
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newDeclaredInstance(Class<T> clazz) {
        try {
            return getDeclaredConstructor(clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * isSuper
     *
     * @param superClazz
     * @param extendClazz
     * @return
     */
    public static boolean isSuper(Class<?> superClazz, Class<?> extendClazz) {
        return superClazz.isAssignableFrom(extendClazz);
    }

    /**
     * isSuper
     *
     * @param superClazz
     * @param extendClassName
     * @return
     */
    public static boolean isSuper(Class<?> superClazz, String extendClassName) {
        try {
            Class<?> extendClazz = Class.forName(extendClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
     *
     * @param superClassName
     * @param extendClazz
     * @return
     */
    public static boolean isSuper(String superClassName, Class<?> extendClazz) {
        try {
            Class<?> superClazz = Class.forName(superClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
     *
     * @param superClassName
     * @param extendClassName
     * @return
     */
    public static boolean isSuper(String superClassName, String extendClassName) {
        try {
            Class<?> superClazz = Class.forName(superClassName);
            Class<?> extendClazz = Class.forName(extendClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * collectSuperObject
     *
     * @param clazz
     * @param includeClass
     * @return
     */
    private static List<Class<?>> collectSuperObject(Class<?> clazz, boolean includeClass) {
        List<Class<?>> clazzList = CollectionSugar.asList(clazz);
        return collectSuperObject(clazzList, includeClass);
    }

    /**
     * collectSuperObject
     *
     * @param clazzList
     * @param includeClass
     * @return
     */
    private static List<Class<?>> collectSuperObject(List<Class<?>> clazzList, boolean includeClass) {
        List<Class<?>> collect = clazzList
                .stream()
                .filter(c -> c.getInterfaces().length > 0 || c.getSuperclass() != null)
                .flatMap(c -> {

                    List<Class<?>> list = new ArrayList();
                    List<Class<?>> interfaces = CollectionSugar.asList(c.getInterfaces());
                    if (!CollectionSugar.isEmpty(interfaces))
                        list.addAll(interfaces);

                    List<Class<?>> superInterfaces = CollectionSugar.asList(c.getInterfaces());
                    if (!CollectionSugar.isEmpty(superInterfaces))
                        list.addAll(collectSuperObject(superInterfaces, true));

                    Class<?> superclass = c.getSuperclass();
                    if (superclass != null)
                        list.addAll(collectSuperObject(superclass, true));

                    return list.stream();
                })
                .distinct()
                .collect(Collectors.toList());

        if (!includeClass)
            collect = collect.stream().filter(c -> c.isInterface()).collect(Collectors.toList());

        return collect;
    }

    /**
     * collectSuperInterfaces
     *
     * @param clazz
     * @return
     */
    public static Class<?>[] collectSuperInterfaces(Class<?> clazz) {
        List<Class<?>> classList = collectSuperObject(clazz, false);
        return classList.toArray(new Class<?>[0]);
    }
//
//    public static Class<?>[] collectSuperInterfaces(Class<?> clazz, Class<Object> returnClazz) {
//        final List<Class<?>> classList = collectSuperObject(clazz, false);
//        return CollectionSugar.toArray(classList, returnClazz);
//    }


    private static boolean matchTypes(Object[] parameters, Class<?>[] parameterTypes) {
        boolean match = true;
        if (parameters == null || parameters.length != parameterTypes.length)
            match = false;
        else {
            for (int i = 0; i < parameters.length; i++) {
                if (parameterTypes[i].isPrimitive()) {
                    if (isSimilarPrimitiveOrWrapper(parameterTypes[i], parameters[i].getClass()))
                        match = true;
                    else
                        match = false;
                } else {
                    if (parameterTypes[i].equals(parameters[i].getClass()))
                        match = true;
                    else
                        match = false;
                }
            }
        }
        return match;
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) ||
                !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) && !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }

    public static void makeAccessible(Field field) {
        if ((!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    public abstract static class Fields {
        public static HashMap<Field, ?> getDeclaredFieldMaps(Object obj, Class<?> clazz) {
            Field[] declaredFields = clazz.getDeclaredFields();
            return (HashMap<Field, ?>) Arrays.stream(declaredFields).collect(Collectors.toMap(field -> field, field -> {
                try {
                    makeAccessible(field);
                    return field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }));
        }

        public static <R> R getDeclaredFieldValue(Object obj, Class<?> clazz, String name) {
            try {
                Field field = clazz.getDeclaredField(name);
                makeAccessible(field);
                R ret = (R) field.get(obj);
                return ret;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static <R> R getDeclaredFieldValue(Object obj, String name) {
            return getDeclaredFieldValue(obj, obj.getClass(), name);
        }

        public static HashMap<Field, ?> getFieldValues(Object obj, Class<?> clazz) {
            Field[] fields = clazz.getFields();
            return (HashMap) Arrays.stream(fields).collect(Collectors.toMap(field -> field, field -> {
                try {
                    makeAccessible(field);
                    return field.get(obj);
                } catch (IllegalAccessException e) {
//                e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }));
        }

        public static <R> R getFieldValue(Object obj, Class<?> clazz, String name) {
            try {
                Field field = clazz.getField(name);
                makeAccessible(field);
                R ret = (R) field.get(obj);
                return ret;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static <R> R getFieldValue(Object obj, String name) {
            return getFieldValue(obj, obj.getClass(), name);
        }

        public static void setFieldValue(Object obj, Class<?> clazz, String name, Object value) {
            try {
                Field field = clazz.getField(name);
                makeAccessible(field);
                field.set(obj, value);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static void setFieldValue(Object obj, String name, Object value) {
            setFieldValue(obj, obj.getClass(), name, value);
        }

        public static void setDeclaredFieldValue(Object obj, Class<?> clazz, String name, Object value) {
            try {
                Field field = clazz.getDeclaredField(name);
                makeAccessible(field);
                field.set(obj, value);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        public static void setDeclaredFieldValue(Object obj, String name, Object value) {
            setDeclaredFieldValue(obj, obj.getClass(), name, value);
        }

        public static Field getDeclaredFieldIncludeParents(Class<?> clazz, String name) {
            List<String> fieldNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().map(Field::getName).collect(Collectors.toList());
            Field field;
            try {
                if (fieldNameList.contains(name)) {
                    field = clazz.getDeclaredField(name);
                } else {
                    Class<?> parentClazz = clazz.getSuperclass();
                    if (parentClazz == null) {
                        throw new NoSuchFieldException(name);
                    } else {
                        field = getDeclaredFieldIncludeParents(parentClazz, name);
                    }
                }
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            return field;
        }

        public static List<Field> getDeclaredFieldIncludeParents(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().collect(Collectors.toList());
            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<Field> parentFieldList = getDeclaredFieldIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentFieldList))
                    fieldList.addAll(parentFieldList);
            }
            return fieldList;
        }

        public static Field getFieldIncludeParents(Class<?> clazz, String name) {
            List<String> fieldNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().map(Field::getName).collect(Collectors.toList());
            Field field;
            if (fieldNameList.contains(name)) {
                try {
                    field = clazz.getField(name);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                field = getDeclaredFieldIncludeParents(parentClazz, name);
            }
            return field;
        }

        public static List<Field> getFieldIncludeParents(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().collect(Collectors.toList());
            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<Field> parentFieldList = getFieldIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentFieldList))
                    fieldList.addAll(parentFieldList);
            }
            return fieldList;
        }
    }

    private static <T> SerializedLambda getSerializedLambda(InstanceGetter<T> fn) {
        SerializedLambda serializedLambda = ClassSugar.Methods.invoke(fn, "writeReplace");
        return serializedLambda;
    }

    private static <T> SerializedLambda getSerializedLambda(InstanceSetter<T> fn) {
        SerializedLambda serializedLambda = ClassSugar.Methods.invoke(fn, "writeReplace");
        return serializedLambda;
    }

    private static <T> SerializedLambda getSerializedLambda(ClassGetter<T, ?> fn) {
        SerializedLambda serializedLambda = ClassSugar.Methods.invoke(fn, "writeReplace");
        return serializedLambda;
    }

    private static <T> SerializedLambda getSerializedLambda(ClassSetter<T, ?> fn) {
        SerializedLambda serializedLambda = ClassSugar.Methods.invoke(fn, "writeReplace");
        return serializedLambda;
    }




    public abstract static class PropertyDescriptors {
        public static String[] getNullPropertyNames(Object bean) {
            //1.获取Bean
            BeanWrapper srcBean = new BeanWrapperImpl(bean);
            //2.获取Bean的属性描述
            PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
            //3.获取Bean的空属性
            Set<String> properties = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : pds) {
                String propertyName = propertyDescriptor.getName();
                Object propertyValue = srcBean.getPropertyValue(propertyName);
                if (propertyValue == null) {
                    srcBean.setPropertyValue(propertyName, null);
                    properties.add(propertyName);
                }
            }
            return properties.toArray(new String[0]);
        }

        public static String[] getAllPropertyNames(Class<?> clazz) {
            Map<String, Class<?>> stringClassMap = toNameClassMap(clazz);
            return stringClassMap.keySet().toArray(new String[0]);
        }

        public static Object getPropertyValue(Object bean, String propertyName) {
            BeanWrapper srcBean = new BeanWrapperImpl(bean);
            return srcBean.getPropertyValue(propertyName);
        }

        public static void copyNotNullProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target, PropertyDescriptors.getNullPropertyNames(source));
        }

        public static void copyAllProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target);
        }

        public static void copyPropertiesOnly(Object source, Object target, List<String> onlyPropertyList) {
            List<String> allPropertyNameList = Arrays.asList(PropertyDescriptors.getAllPropertyNames(source.getClass()));
            List<String> excludeList = CollectionSugar.except(allPropertyNameList, onlyPropertyList);
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludeList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static void copyPropertiesOnly(Object source, Object target, String... onlyProperties) {
            List<String> allPropertyList = Arrays.asList(PropertyDescriptors.getAllPropertyNames(source.getClass()));
            List<String> onlyPropertyList = Arrays.asList(onlyProperties);
            List<String> excludeList = CollectionSugar.except(allPropertyList, onlyPropertyList);
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludeList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static <S, T> void copyPropertiesOnly(S s, T t, ClassGetter<S, ?>... properties) {
            List<String> propertyList = Arrays.stream(properties).map(c -> ClassSugar.PropertyDescriptors.getPropertyNameOf(c)).collect(Collectors.toList());
            copyPropertiesOnly(s, t, propertyList);
        }

        public static void copyAllPropertiesExclude(Object source, Object target, List<String> excludePropertyList) {
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludePropertyList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static void copyAllPropertiesExclude(Object source, Object target, String... excludeProperties) {
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static <S, T> void copyAllPropertiesExclude(S s, T t, ClassGetter<S, ?>... properties) {
            List<String> propertyList = Arrays.stream(properties).map(c -> ClassSugar.PropertyDescriptors.getPropertyNameOf(c)).collect(Collectors.toList());
            copyAllPropertiesExclude(s, t, propertyList);
        }




        public static PropertyDescriptor getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz, String name) {
            List<String> propertyDescriptorNameList = new ArrayList<>(Arrays.asList(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))).stream().map(PropertyDescriptor::getName).collect(Collectors.toList());
            PropertyDescriptor propertyDescriptor;
            if (propertyDescriptorNameList.contains(name)) {
                propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(clazz, name);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                propertyDescriptor = getDeclaredPropertyDescriptorIncludeParents(parentClazz, name);
            }
            return propertyDescriptor;
        }

        public static List<PropertyDescriptor> getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz) {
            List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>(Arrays.asList(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz)));

            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<PropertyDescriptor> parentPropertyDescriptorList = getDeclaredPropertyDescriptorIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentPropertyDescriptorList))
                    propertyDescriptorList.addAll(parentPropertyDescriptorList);
            }
            return propertyDescriptorList;
        }

        public static boolean allIsNull(Object bean) {
            Map<String, Object> stringObjectMap = ClassSugar.PropertyDescriptors.toNameValueMap(bean);
            Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
            boolean b = entries.stream().allMatch(c -> c.getValue() == null);
            return b;
        }

        public static boolean allReferenceIsNull(Object bean) {
            Map<String, PropertyDescriptor> namePropertyMap = ClassSugar.PropertyDescriptors.toNamePropertyMap(bean);
            Set<Map.Entry<String, PropertyDescriptor>> entries = namePropertyMap.entrySet();
            boolean b = entries.stream()
                    .filter(c -> Object.class.isAssignableFrom(c.getValue().getPropertyType()))
                    .allMatch(c -> ClassSugar.PropertyDescriptors.getValue(bean, c.getValue()) == null);
            return b;
        }

        private static boolean isBuiltIn(PropertyDescriptor propertyDescriptor, Object bean) {
            return propertyDescriptor.getName().equals("class")
                    || (propertyDescriptor.getName().equals("beanFactory") && DynamicProxySugar.isCglibProxy(bean));
        }

        public static Map<String, Class<?>> toNameClassMap(Class<?> clazz) {
            Map<String, Class<?>> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
                    .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
            return map;
        }


        public static Map<String, Object> toNameValueMap(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }

        public static Map<String, Object> toNameValueMapExceptNull(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean) && getValue(bean, c) != null)
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }


        public static Map<String, PropertyDescriptor> toNamePropertyMap(Object bean) {
            Map<String, PropertyDescriptor> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }

        public static Map<String, PropertyDescriptor> toNamePropertyMapExceptNull(Object bean) {
            Map<String, PropertyDescriptor> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean) && getValue(bean, c) != null)
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }


        public static Map<String, String> toNameStringMap(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public static Map<String, String> toNameStringMapExceptNull(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public static PropertyDescriptor getPropertyDescriptor(Object entity, String propertyName) {
            return BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName);
        }

        public static Object getValue(Object bean, PropertyDescriptor propertyDescriptor) {
            try {
                return propertyDescriptor.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public static Object getValue(Object bean, String propertyName) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                return propertyDescriptor.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public static Object setValue(Object bean, String propertyName, Object value) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                return propertyDescriptor.getWriteMethod().invoke(bean, value);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public static Object setValueIfPresent(Object bean, String propertyName, Object value) {
            try {
                if (exist(bean, propertyName)) {
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                    return propertyDescriptor.getWriteMethod().invoke(bean, value);
                } else
                    return null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public static boolean exist(Object bean, String propertyName) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
            return propertyDescriptor != null;
        }

        public static <T extends Annotation> T getAnnotationIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor, Class<T> annotationClass) {
            String fieldName = propertyDescriptor.getName();
            return ClassSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).getAnnotation(annotationClass);
        }

        public static Annotation[] getAnnotationsIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor) {
            String fieldName = propertyDescriptor.getName();
            return ClassSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).getAnnotations();
        }

        public static <T extends Annotation> Boolean isAnnotationPresentIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor, Class<T> annotationClass) {
            String fieldName = propertyDescriptor.getName();
            return ClassSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).isAnnotationPresent(annotationClass);
        }

        public static <T> String getPropertyNameOf(InstanceGetter<T> fn) {
            String methodName = getMethodNameOf(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyNameOf(InstanceSetter<T> fn) {
            String methodName = getMethodNameOf(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyNameOf(ClassGetter<T, ?> fn) {
            String methodName = getMethodNameOf(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyNameOf(ClassSetter<T, ?> fn) {
            String methodName = getMethodNameOf(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        private static <T> String getMethodNameOf(InstanceGetter<T> fn) {
            SerializedLambda serializedLambda = ClassSugar.getSerializedLambda(fn);
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodNameOf(InstanceSetter<T> fn) {
            SerializedLambda serializedLambda = ClassSugar.getSerializedLambda(fn);
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodNameOf(ClassGetter<T, ?> fn) {
            SerializedLambda serializedLambda = ClassSugar.getSerializedLambda(fn);
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodNameOf(ClassSetter<T, ?> fn) {
            SerializedLambda serializedLambda = ClassSugar.getSerializedLambda(fn);
            return serializedLambda.getImplMethodName();
        }

        private static String convertMethodNameToPropertyName(String methodName) {
            if (methodName.startsWith("is")) {
                methodName = methodName.substring(2);
            } else if (methodName.startsWith("get") || methodName.startsWith("set")) {
                methodName = methodName.substring(3);
            } else {
                throw new RuntimeException("Error parsing property name '" + methodName + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            if (methodName.length() == 1 || ((methodName.length() > 1 && Character.isLowerCase(methodName.charAt(1))))) {
                methodName = methodName.substring(0, 1).toLowerCase(Locale.ENGLISH) + methodName.substring(1);
            }

            return methodName;
        }


        /**
         * spell eg. spell("{0}:{1}", " ", book::getAuthor, book::getDescription, book::getName);
         *
         * @param pattern
         * @param delimiter
         * @param fnArray
         * @param <T>
         * @return e. author:auth description:desc
         */
        public static <T> String spell(String pattern, String delimiter, InstanceGetter<T>... fnArray) {
            StringJoiner sb = new StringJoiner(delimiter);
            Arrays.stream(fnArray).forEach(c -> {
                if (c.get() != null) {
                    String name = ClassSugar.PropertyDescriptors.getPropertyNameOf(c);
                    T value = c.get();
                    sb.add(MessageFormat.format(pattern, name, value));
                }
            });
            return sb.toString();
        }

//        public static <T> String spell(LambdaSupplier<T>... fnArray) {
//            return spell("{0}:{1}", " ", fnArray);
//        }
    }

    public abstract static class Methods {
        public static Method getDeclaredMethod(Class<?> clazz, String methodName) {
            try {
                Method method = clazz.getDeclaredMethod(methodName);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public static Method getMethod(Class<?> clazz, String methodName) {
            try {
                Method method = clazz.getMethod(methodName);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public static Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
            try {
                Method method = clazz.getMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public static <R> R invoke(Object obj, Class<?> clazz, String methodName) {
            try {
                Method method = clazz.getDeclaredMethod(methodName);
                makeAccessible(method);
                R ret = (R) method.invoke(obj);
                return ret;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        public static <R> R invoke(Object obj, String methodName) {
            return invoke(obj, obj.getClass(), methodName);
        }

        public static <R> R invoke(Object obj, Class<?> clazz, String methodName, Object... parameters) {
            Class<?>[] parameterTypes = new Class<?>[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
            return invoke(obj, clazz, methodName, parameterTypes, parameters);
        }

        public static <R> R invoke(Object obj, String methodName, Object... parameters) {
            return invoke(obj, obj.getClass(), methodName, parameters);
        }

        public static <R> R invoke(Object obj, Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                R ret = (R) method.invoke(obj, parameters);
                return ret;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        public static <R> R invoke(Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
            return invoke(obj, obj.getClass(), methodName, parameterTypes, parameters);
        }

        public static Method getDeclaredMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) {
            List<String> methodNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods())).stream().map(Method::getName).collect(Collectors.toList());
            Method method;
            if (methodNameList.contains(name)) {
                try {
                    method = clazz.getDeclaredMethod(name, parameterTypes);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                method = getDeclaredMethodIncludeParents(parentClazz, name, parameterTypes);
            }
            return method;
        }

        public static Method getMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) {
            List<String> methodNameList = new ArrayList<>(Arrays.asList(clazz.getMethods())).stream().map(Method::getName).collect(Collectors.toList());
            Method method;
            if (methodNameList.contains(name)) {
                try {
                    method = clazz.getMethod(name, parameterTypes);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                method = getMethodIncludeParents(parentClazz, name, parameterTypes);
            }
            return method;
        }
    }

    /**
     * Resolve the type arguments of the given generic interface against the given
     * target class which is assumed to implement the generic interface and possibly
     * declare concrete types for its type variables.
     * @param clazz the target class to check against
     * @param genericIfc the generic interface or superclass to resolve the type argument from
     * @return the resolved type of each argument, with the array size matching the
     * number of actual type arguments, or {@code null} if not resolvable
     */
    @Nullable
    public static Class<?>[] resolveTypeArguments(Class<?> clazz, Class<?> genericIfc) {
//        ResolvableType.forClass()
        return GenericTypeResolver.resolveTypeArguments(clazz, genericIfc);
    }

    /**
     * Resolve the single type argument of the given generic interface against the given
     * target method which is assumed to return the given interface or an implementation
     * of it.
     * @param method the target method to check the return type of
     * @param genericIfc the generic interface or superclass to resolve the type argument from
     * @return the resolved parameter type of the method return type, or {@code null}
     * if not resolvable or if the single argument is of type {@link WildcardType}.
     */
    @Nullable
    public static Class<?> resolveReturnTypeArgument(Method method, Class<?> genericIfc) {
//        ResolvableType.forClass()
        return GenericTypeResolver.resolveReturnTypeArgument(method, genericIfc);
    }

    public static ParameterizedType getGenericReturnType(Method method) {
        return (ParameterizedType) method.getGenericReturnType();
    }

    public static Type[] getActualTypeArgumentsOfGenericReturnType(Method method) {
        return ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments();
    }

    public static <T> List<T> getSPIList(Class<T> iface) {
        Iterator<T> iterator = ServiceLoader.load(iface).iterator();
        List<T> list = CollectionSugar.toList(iterator);
        return list;
    }

    public static boolean isMemberClass(Class<?> clazz) {
//        return (clazz.isMemberClass() && !Modifier.isStatic(clazz.getModifiers()));
//        return org.springframework.util.ClassUtils.isInnerClass(clazz);
        return clazz.isMemberClass();
    }


}
