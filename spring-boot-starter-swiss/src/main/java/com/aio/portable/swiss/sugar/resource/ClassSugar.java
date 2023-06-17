package com.aio.portable.swiss.sugar.resource;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.io.File;
import java.io.Serializable;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class ClassSugar {

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap();

    static {
        primitiveWrapperTypeMap.put(Byte.class, Byte.TYPE);
        primitiveWrapperTypeMap.put(Character.class, Character.TYPE);
        primitiveWrapperTypeMap.put(Double.class, Double.TYPE);
        primitiveWrapperTypeMap.put(Float.class, Float.TYPE);
        primitiveWrapperTypeMap.put(Integer.class, Integer.TYPE);
        primitiveWrapperTypeMap.put(Long.class, Long.TYPE);
        primitiveWrapperTypeMap.put(Short.class, Short.TYPE);
        primitiveWrapperTypeMap.put(Void.class, Void.TYPE);

        primitiveWrapperTypeMap.put(Byte[].class, byte[].class);
        primitiveWrapperTypeMap.put(Character[].class, char[].class);
        primitiveWrapperTypeMap.put(Double[].class, double[].class);
        primitiveWrapperTypeMap.put(Float[].class, float[].class);
        primitiveWrapperTypeMap.put(Integer[].class, int[].class);
        primitiveWrapperTypeMap.put(Long[].class, long[].class);
        primitiveWrapperTypeMap.put(Short[].class, short[].class);
    }

    public static final boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static final boolean isPrimitiveWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return primitiveWrapperTypeMap.containsKey(clazz);
    }

    public static final boolean similarPrimitive(Class<?> clazz1, Class<?> clazz2) {
        boolean isPrimitiveOrWrapper = isPrimitiveOrWrapper(clazz1) && isPrimitiveOrWrapper(clazz2);
        if (!isPrimitiveOrWrapper)
            return false;
        Class<?> targetClazz1 = primitiveWrapperTypeMap.getOrDefault(clazz1, clazz1);
        Class<?> targetClazz2 = primitiveWrapperTypeMap.getOrDefault(clazz2, clazz2);
        return targetClazz1.equals(targetClazz2);

    }

    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return
     */
    public static final String getPath(final Class<?> clazz) {
        String clazzFile = convertClassNameToResourceLocation(clazz.getTypeName());
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
            ClassLoader clsLoader = clazz.getClassLoader();
            location = clsLoader != null ?
                    clsLoader.getResource(clazzFile) :
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
    public static final String getShortName(String className) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(className);
        return shortClassName;
    }

    /**
     * getClassFileName -> org.springframework.util.ClassUtils
     *
     * @param clazz
     * @return
     */
    public static final String getClassFileName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getClassFileName(clazz);
        return classFileName;
    }

    /**
     * getPackageName -> org.springframework.util.ClassUtils.getPackageName
     *
     * @param clazz
     * @return
     */
    public static final String getPackageName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getPackageName(clazz);
        return classFileName;
    }


    /**
     * getBeanName -> Introspector.decapitalize
     *
     * @param shortClassName
     * @return
     */
    public static final String spellBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }


    public static final ArrayList<Class<?>> listLoadedClasses() {
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
//     * convertClassName2ResourceLocation
//     *
//     * @param className className/packageName eg. com.company.biz | com.company.biz.Book
//     * @return com/company/biz | com/company/biz/Book.class
//     */
//    public static final String convertClassNameToResourceLocation(String className) {
//        String path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(className).concat(".class");
//        return path;
//    }

    /**
     * convertClassNameToLocation
     *
     * @param className com.company.biz | com.company.biz.Book
     * @return className/packageName eg. com/company/biz | com/company/biz/Book.class
     */
    public static final String convertClassNameToResourceLocation(String className) {
        String temp = className.replace(".", "/");
        String lastWord = StringSugar.getLastWord(temp, "/");
        temp = StringSugar.isCapitalize(lastWord) ? temp + ".class" : temp;
        return temp;
    }

    /**
     * convertLocationToClassName
     *
     * @param location className/packageName eg. com/company/biz | com/company/biz/Book.class
     * @return com.company.biz | com.company.biz.Book
     */
    public static final String convertResourceLocationToClassName(String location) {
        return ResourceSugar.convertResourceLocationToClassName(location);
    }


    /**
     * getConstructor
     *
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     */
    private static final <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
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
    private static final <T> Constructor<T> getDeclaredConstructor(Class<T> clazz, Class<?>... parameterTypes) {
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
    public static final <T> T newInstance(Class<T> clazz,
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
    public static final <T> T newDeclaredInstance(Class<T> clazz,
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
    public static final <T> T newInstance(Class<T> clazz) {
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
    public static final <T> T newDeclaredInstance(Class<T> clazz) {
        try {
            return getDeclaredConstructor(clazz).newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSimpleValueType
     *
     * @param clazz
     * @return
     */
    public static final boolean isSimpleValueType(Class<?> clazz) {
        return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz) || Enum.class.isAssignableFrom(clazz) || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz || URL.class == clazz || Locale.class == clazz || Class.class == clazz;
    }

    /**
     * isSuper
     *
     * @param superClazz
     * @param extendClazz
     * @return
     */
    public static final boolean isSuper(Class<?> superClazz, Class<?> extendClazz) {
        return superClazz.isAssignableFrom(extendClazz);
    }

    /**
     * isSuper
     *
     * @param superClazz
     * @param extendClassName
     * @return
     */
    public static final boolean isSuper(Class<?> superClazz, String extendClassName) {
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
    public static final boolean isSuper(String superClassName, Class<?> extendClazz) {
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
    public static final boolean isSuper(String superClassName, String extendClassName) {
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
    private static final List<Class<?>> collectSuperObject(Class<?> clazz, boolean includeClass) {
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
    private static final List<Class<?>> collectSuperObject(List<Class<?>> clazzList, boolean includeClass) {
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
    public static final Class<?>[] collectSuperInterfaces(Class<?> clazz) {
        List<Class<?>> classList = collectSuperObject(clazz, false);
        return classList.toArray(new Class<?>[0]);
    }
//
//    public static Class<?>[] collectSuperInterfaces(Class<?> clazz, Class<Object> returnClazz) {
//        final List<Class<?>> classList = collectSuperObject(clazz, false);
//        return CollectionSugar.toArray(classList, returnClazz);
//    }


    private static final boolean matchTypes(Object[] parameters, Class<?>[] parameterTypes) {
        boolean match = true;
        if (parameters == null || parameters.length != parameterTypes.length)
            match = false;
        else {
            for (int i = 0; i < parameters.length; i++) {
                if (parameterTypes[i].isPrimitive()) {
                    if (similarPrimitive(parameterTypes[i], parameters[i].getClass()))
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

    public static final void makeAccessible(Constructor<?> constructor) {
        ReflectionUtils.makeAccessible(constructor);
    }

    public static final void makeAccessible(Method method) {
        ReflectionUtils.makeAccessible(method);
    }

    public static final void makeAccessible(Field field) {
        ReflectionUtils.makeAccessible(field);
    }

    public static final HashMap<Field, ?> getDeclaredFields(Object obj, Class<?> clazz) {
        Field[] declaredFields = clazz.getDeclaredFields();
        return (HashMap) Arrays.stream(declaredFields).collect(Collectors.toMap(field -> field, field -> {
            try {
                makeAccessible(field);
                return field.get(obj);
            } catch (IllegalAccessException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }

        }));
    }

    public static final <R> R getDeclaredField(Object obj, Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            makeAccessible(field);
            R ret = (R) field.get(obj);
            return ret;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <R> R getDeclaredField(Object obj, String name) {
        return getDeclaredField(obj, obj.getClass(), name);
    }

    public static final HashMap<Field, ?> getFields(Object obj, Class<?> clazz) {
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

    public static final <R> R getField(Object obj, Class<?> clazz, String name) {
        try {
            Field field = clazz.getField(name);
            makeAccessible(field);
            R ret = (R) field.get(obj);
            return ret;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <R> R getField(Object obj, String name) {
        return getField(obj, obj.getClass(), name);
    }

    public static final Method getDeclaredMethod(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            makeAccessible(method);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            makeAccessible(method);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Method getMethod(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getMethod(methodName);
            makeAccessible(method);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Method getMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            makeAccessible(method);
            return method;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <R> R invoke(Object obj, Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            makeAccessible(method);
            R ret = (R) method.invoke(obj);
            return ret;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <R> R invoke(Object obj, String methodName) {
        return invoke(obj, obj.getClass(), methodName);
    }

    public static final <R> R invoke(Object obj, Class<?> clazz, String methodName, Object... parameters) {
        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            parameterTypes[i] = parameters[i].getClass();
        }
        return invoke(obj, clazz, methodName, parameterTypes, parameters);
    }

    public static final <R> R invoke(Object obj, String methodName, Object... parameters) {
        return invoke(obj, obj.getClass(), methodName, parameters);
    }

    public static final <R> R invoke(Object obj, Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            makeAccessible(method);
            R ret = (R) method.invoke(obj, parameters);
            return ret;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <R> R invoke(Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        return invoke(obj, obj.getClass(), methodName, parameterTypes, parameters);
    }

    @Nullable
    public static Class<?>[] resolveSuperClassArgumentType(Class<?> clazz, Class<?> genericIfc) {
//        ResolvableType.forClass()
        return GenericTypeResolver.resolveTypeArguments(clazz, genericIfc);
    }

    public static final <T> List<T> getSPIList(Class<T> interfaze) {
        Iterator<T> iterator = ServiceLoader.load(interfaze).iterator();
        List<T> list = CollectionSugar.toList(iterator);
        return list;
    }
}
