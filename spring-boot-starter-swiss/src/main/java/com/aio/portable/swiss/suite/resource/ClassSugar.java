package com.aio.portable.swiss.suite.resource;

import com.aio.portable.swiss.sugar.CollectionSugar;
import org.springframework.util.ReflectionUtils;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public abstract class ClassSugar {
    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return
     */
    public final static String getPath(final Class<?> clazz) {
        final String clazzFile = convertCompleteName2ResourcePath(clazz.getTypeName());
        URL location = null;
        final ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            final CodeSource cs = domain.getCodeSource();
            if (cs != null) location = cs.getLocation();
            if (location != null) {
                if (org.springframework.util.ResourceUtils.URL_PROTOCOL_FILE.equals(location.getProtocol())) {
                    if (location.toExternalForm().endsWith(".jar") ||
                            location.toExternalForm().endsWith(".zip"))
                        try {
                            location = new URL((org.springframework.util.ResourceUtils.URL_PROTOCOL_JAR + ":").concat(location.toExternalForm())
                                    .concat("!/").concat(clazzFile));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    else if (new File(location.getFile()).isDirectory())
                        try {
                            location = new URL(location, clazzFile);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
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
     * hasClassByCurrentThreadClassLoader 判断是否存在某一个类
     *
     * @param completeClassName eg. com.art.Book
     * @return
     * @throws IOException
     */
    public static boolean exist(String completeClassName) {
        String resource = convertCompleteName2ResourcePath(completeClassName);
        return ResourceSugar.ByClassLoader.existResource(resource);
    }


    /**
     * getShortName -> org.springframework.util.ClassUtils
     * @param completeClassName
     * @return
     */
    public static String getShortName(String completeClassName) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(completeClassName);
        return shortClassName;
    }

    /**
     * getClassFileName -> org.springframework.util.ClassUtils
     * @param clazz
     * @return
     */
    public static String getClassFileName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getClassFileName(clazz);
        return classFileName;
    }

    /**
     * getPackageName -> org.springframework.util.ClassUtils.getPackageName
     * @param clazz
     * @return
     */
    public static String getPackageName(Class<?> clazz) {
        String classFileName = org.springframework.util.ClassUtils.getPackageName(clazz);
        return classFileName;
    }


    /**
     * getBeanName -> Introspector.decapitalize
     * @param shortClassName
     * @return
     */
    public static String getBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }


    /**
     * convertCompleteName2ResourcePath
     *
     * @param completeName className/packageName eg. com.company.biz | com.company.biz.Book
     * @return com/company/biz | com/company/biz/Book
     */
    public static String convertCompleteName2ResourcePath(String completeName) {
        String path;
//        path = completeName.replace('.', '/').concat(".class");
        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(completeName);
        path = path.concat(".class");
        return path;
    }


//    /**
//     * convertCompleteName2ResourceFilePath
//     *
//     * @param completeName className/packageName eg. com.company.biz | com.company.biz.Book
//     * @return com/company/biz | com/company/biz/Book
//     */
//    private static String convertCompleteName2ResourceFilePath(String completeName) {
//        String path;
////        path = fullName.replace('.', '/').concat(".class");
//        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(completeName).concat(".class");
//        return path;
//    }


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
            e.printStackTrace();
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * isSimpleValueType
     * @param clazz
     * @return
     */
    public static boolean isSimpleValueType(Class<?> clazz) {
        return org.springframework.util.ClassUtils.isPrimitiveOrWrapper(clazz) || Enum.class.isAssignableFrom(clazz) || CharSequence.class.isAssignableFrom(clazz) || Number.class.isAssignableFrom(clazz) || Date.class.isAssignableFrom(clazz) || URI.class == clazz || URL.class == clazz || Locale.class == clazz || Class.class == clazz;
    }

    /**
     * isSuper
     * @param superClazz
     * @param extendClazz
     * @return
     */
    public static boolean isSuper(Class<?> superClazz, Class<?> extendClazz) {
        return superClazz.isAssignableFrom(extendClazz);
    }

    /**
     * isSuper
     * @param superClazz
     * @param extendClassName
     * @return
     */
    public static boolean isSuper(Class<?> superClazz, String extendClassName) {
        Class<?> extendClazz = null;
        try {
            extendClazz = Class.forName(extendClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
     * @param superClassName
     * @param extendClazz
     * @return
     */
    public static boolean isSuper(String superClassName, Class<?> extendClazz) {
        try {
            Class<?> superClazz = Class.forName(superClassName);
            return superClazz.isAssignableFrom(extendClazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * isSuper
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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * collectSuperObject
     * @param clazz
     * @param includeClass
     * @return
     */
    private static List<Class<?>> collectSuperObject(Class<?> clazz, boolean includeClass) {
        List<Class<?>> clazzList = new ArrayList<>(CollectionSugar.toList(clazz));
        return collectSuperObject(clazzList, includeClass);
    }

    /**
     * collectSuperObject
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
    public static Class<?>[] collectSuperInterfaces(Class<?> clazz) {
        final List<Class<?>> classList = collectSuperObject(clazz, false);
        return classList.toArray(new Class<?>[0]);
    }
//
//    public static Class<?>[] collectSuperInterfaces(Class<?> clazz, Class<Object> returnClazz) {
//        final List<Class<?>> classList = collectSuperObject(clazz, false);
//        return CollectionSugar.toArray(classList, returnClazz);
//    }
}
