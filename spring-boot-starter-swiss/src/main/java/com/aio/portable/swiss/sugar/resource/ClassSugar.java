package com.aio.portable.swiss.sugar.resource;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ClassSugar {
    private final static Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap(8);

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

    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return clazz.isPrimitive() || isPrimitiveWrapper(clazz);
    }

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        Assert.notNull(clazz, "Class must not be null");
        return primitiveWrapperTypeMap.containsKey(clazz);
    }

    public static boolean similarPrimitive(Class<?> clazz1, Class<?> clazz2) {
        boolean isPrimitiveOrWrapper = isPrimitiveOrWrapper(clazz1) && isPrimitiveOrWrapper(clazz2);
        if (!isPrimitiveOrWrapper)
            return false;
        Class<?> targetClazz1 = primitiveWrapperTypeMap.getOrDefault(clazz1, clazz1);
        Class<?> targetClazz2 = primitiveWrapperTypeMap.getOrDefault(clazz2, clazz2);
        return targetClazz1.equals(targetClazz2);

    }

    /**
     * 获取类所有的路径
     * @param clazz
     * @return
     */
    public final static String getPath(final Class<?> clazz) {
        final String clazzFile = convertClassNameToResourceLocation(clazz.getTypeName());
        URL location = null;
        final ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            final CodeSource cs = domain.getCodeSource();
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
            final ClassLoader clsLoader = clazz.getClassLoader();
            location = clsLoader != null ?
                    clsLoader.getResource(clazzFile) :
                    ClassLoader.getSystemResource(clazzFile);
        }
        return location.toString();
    }





    /**
     * getShortName -> org.springframework.util.ClassUtils
     * @param className
     * @return
     */
    public final static String getShortName(String className) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(className);
        return shortClassName;
    }

    /**
     * getClassFileName -> org.springframework.util.ClassUtils
     * @param clazz
     * @return
     */
    public final static String getClassFileName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getClassFileName(clazz);
        return classFileName;
    }

    /**
     * getPackageName -> org.springframework.util.ClassUtils.getPackageName
     * @param clazz
     * @return
     */
    public final static String getPackageName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getPackageName(clazz);
        return classFileName;
    }


    /**
     * getBeanName -> Introspector.decapitalize
     * @param shortClassName
     * @return
     */
    public final static String getBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }


//    /**
//     * convertClassName2ResourceLocation
//     *
//     * @param className className/packageName eg. com.company.biz | com.company.biz.Book
//     * @return com/company/biz | com/company/biz/Book.class
//     */
//    public final static String convertClassNameToResourceLocation(String className) {
//        String path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(className).concat(".class");
//        return path;
//    }

    /**
     * convertClassNameToLocation
     * @param className com.company.biz | com.company.biz.Book
     * @return className/packageName eg. com/company/biz | com/company/biz/Book.class
     */
    public final static String convertClassNameToResourceLocation(String className) {
        String temp = className.replace(".", "/");
        String lastWord = StringSugar.getLastWord(temp, "/");
        temp = StringSugar.isCapitalize(lastWord) ? temp + ".class" : temp;
        return temp;
    }

    /**
     * convertLocationToClassName
     * @param location className/packageName eg. com/company/biz | com/company/biz/Book.class
     * @return com.company.biz | com.company.biz.Book
     */
    public final static String convertResourceLocationToClassName(String location) {
        return ResourceSugar.convertResourceLocationToClassName(location);
    }


    /**
     * newInstance
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     */
    public synchronized final static <T> T newInstance(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes).newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * declaredNewInstance
     * @param clazz
     * @param parameterTypes
     * @param <T>
     * @return
     */
    public synchronized final static <T> T newDeclaredInstance(Class<T> clazz, Class<?>... parameterTypes) {
        try {
            Constructor<T> declaredConstructor = parameterTypes == null ? clazz.getDeclaredConstructor() : clazz.getDeclaredConstructor(parameterTypes);
            ReflectionUtils.makeAccessible(declaredConstructor);
            return declaredConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSimpleValueType
     * @param clazz
     * @return
     */
    public final static boolean isSimpleValueType(Class<?> clazz) {
        return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz) || Enum.class.isAssignableFrom(clazz) || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz || URL.class == clazz || Locale.class == clazz || Class.class == clazz;
    }

    /**
     * isSuper
     * @param superClazz
     * @param extendClazz
     * @return
     */
    public final static boolean isSuper(Class<?> superClazz, Class<?> extendClazz) {
        return superClazz.isAssignableFrom(extendClazz);
    }

    /**
     * isSuper
     * @param superClazz
     * @param extendClassName
     * @return
     */
    public final static boolean isSuper(Class<?> superClazz, String extendClassName) {
        try {
            Class<?> extendClazz = Class.forName(extendClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
     * @param superClassName
     * @param extendClazz
     * @return
     */
    public final static boolean isSuper(String superClassName, Class<?> extendClazz) {
        try {
            Class<?> superClazz = Class.forName(superClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
     * @param superClassName
     * @param extendClassName
     * @return
     */
    public final static boolean isSuper(String superClassName, String extendClassName) {
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
     * @param clazz
     * @param includeClass
     * @return
     */
    private final static List<Class<?>> collectSuperObject(Class<?> clazz, boolean includeClass) {
        List<Class<?>> clazzList = new ArrayList<>(CollectionSugar.toList(clazz));
        return collectSuperObject(clazzList, includeClass);
    }

    /**
     * collectSuperObject
     * @param clazzList
     * @param includeClass
     * @return
     */
    private final static List<Class<?>> collectSuperObject(List<Class<?>> clazzList, boolean includeClass) {
        List<Class<?>> collect = clazzList
                .stream()
                .filter(c -> c.getInterfaces().length > 0 || c.getSuperclass() != null)
                .flatMap(c -> {

                    List<Class<?>> list = new ArrayList();
                    List<Class<?>> interfaces = CollectionSugar.toList(c.getInterfaces());
                    if (!CollectionSugar.isEmpty(interfaces))
                        list.addAll(interfaces);

                    List<Class<?>> superInterfaces = CollectionSugar.toList(c.getInterfaces());
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
     * @param clazz
     * @return
     */
    public final static Class<?>[] collectSuperInterfaces(Class<?> clazz) {
        final List<Class<?>> classList = collectSuperObject(clazz, false);
        return classList.toArray(new Class<?>[0]);
    }
//
//    public static Class<?>[] collectSuperInterfaces(Class<?> clazz, Class<Object> returnClazz) {
//        final List<Class<?>> classList = collectSuperObject(clazz, false);
//        return CollectionSugar.toArray(classList, returnClazz);
//    }




    private final static boolean matchTypes(Object[] parameters, Class<?>[] parameterTypes) {
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
                    if(parameterTypes[i].equals(parameters[i].getClass()))
                        match = true;
                    else
                        match = false;
                }
            }
        }
        return match;
    }

    public final static <R> R invoke(Object obj, String methodName, Object[] parameters) {
        try {
            Class<?>[] parameterTypes = new Class<?>[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                parameterTypes[i] = parameters[i].getClass();
            }
            Method[] methods = obj.getClass().getDeclaredMethods();
            Method method = Arrays.stream(methods)
                    .filter(c -> c.getName().equals(methodName) && matchTypes(parameters, c.getParameterTypes()))
                    .findFirst().get();

            ReflectionUtils.makeAccessible(method);
            R ret = (R) method.invoke(obj, parameters);
            return ret;
        } catch (IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final static <R> R invoke(Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
            ReflectionUtils.makeAccessible(method);
            R ret = (R) method.invoke(obj, parameters);
            return ret;
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final static <T> List<T> getListOfSPI(Class<T> interfaze) {
        Iterator<T> iterator = ServiceLoader.load(interfaze).iterator();
        List<T> list = CollectionSugar.toList(iterator);
        return list;
    }
}
