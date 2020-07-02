package com.aio.portable.swiss.suite.resource;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Date;
import java.util.Locale;

public abstract class ClassSugar {
    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return
     */
    public final static String getPath(final Class<?> clazz) throws MalformedURLException {
        final String clazzFile = convertQualifiedName2ResourcePath(clazz.getTypeName());
        URL location = null;
        final ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            final CodeSource cs = domain.getCodeSource();
            if (cs != null) location = cs.getLocation();
            if (location != null) {
                if (org.springframework.util.ResourceUtils.URL_PROTOCOL_FILE.equals(location.getProtocol())) {
                    if (location.toExternalForm().endsWith(".jar") ||
                            location.toExternalForm().endsWith(".zip"))
                        location = new URL((org.springframework.util.ResourceUtils.URL_PROTOCOL_JAR + ":").concat(location.toExternalForm())
                                .concat("!/").concat(clazzFile));
                    else if (new File(location.getFile()).isDirectory())
                        location = new URL(location, clazzFile);
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
     * @param qualifiedClassName eg. com.art.Book
     * @return
     * @throws IOException
     */
    public static boolean exist(String qualifiedClassName) throws IOException {
        String resource = convertQualifiedName2ResourcePath(qualifiedClassName);
        return ResourceSugar.ByClassLoader.existResource(resource);
    }


    /**
     * getShortName -> org.springframework.util.ClassUtils
     * @param qualifiedClassName
     * @return
     */
    public static String getShortName(String qualifiedClassName) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(qualifiedClassName);
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
     * convertQualifiedName2ResourcePath
     *
     * @param qualifiedName className/packageName eg. com.company.biz | com.company.biz.Book
     * @return com/company/biz | com/company/biz/Book
     */
    public static String convertQualifiedName2ResourcePath(String qualifiedName) {
        String path;
//        path = qualifiedName.replace('.', '/').concat(".class");
        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(qualifiedName);
        path = path.concat(".class");
        return path;
    }


//    /**
//     * convertQualifiedName2ResourceFilePath
//     *
//     * @param qualifiedName className/packageName eg. com.company.biz | com.company.biz.Book
//     * @return com/company/biz | com/company/biz/Book
//     */
//    private static String convertQualifiedName2ResourceFilePath(String qualifiedName) {
//        String path;
////        path = fullName.replace('.', '/').concat(".class");
//        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(qualifiedName).concat(".class");
//        return path;
//    }


    /**
     * newDeclaredInstance
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized final static <T> T newDeclaredInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * newInstance
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized final static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
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


}
