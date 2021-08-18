package com.aio.portable.swiss.suite.resource;


import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public abstract class ClassLoaderSugar {
    /**
     * exist 判断是否存在某一个类
     *
     * @param className eg. com.art.Book
     * @return
     * @throws IOException
     */
    public final static boolean isPresent(String className) {
        String resource = ClassSugar.convertClassNameToResourceLocation(className);
        return ResourceSugar.ByClassLoader.existResource(resource);
    }

    /**
     * findLoadedClass 解析出一个类
     * @param className
     * @param classLoader
     * @return
     */
    public final static Class<?> findLoadedClass(String className, ClassLoader classLoader) {
        try {
            Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
            ReflectionUtils.makeAccessible(method);
            Class<?> clazz = (Class<?>) method.invoke(classLoader, className);
            return clazz;
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public final static Class<?> findLoadedClass(String className) {
        try {
            Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
            ReflectionUtils.makeAccessible(method);
            Class<?> clazz = (Class<?>) method.invoke(ClassLoaderSugar.getDefaultClassLoader(), className);
            return clazz;
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * hasLoaded 判断某一个类是否已加载（指被实例化过，仅声明不会被加载）
     * @param className
     * @param classLoader
     * @return
     */
    public final static boolean hasLoaded(String className, ClassLoader classLoader) {
        boolean hasLoaded = findLoadedClass(className, classLoader) != null ? true : false;
        return hasLoaded;
    }

    /**
     * hasLoaded 判断在某一线程中，某一个类是否已加载（指被实例化过，仅声明不会被加载）
     * @param className
     * @return
     */
    public final static boolean hasLoaded(String className) {
        boolean hasLoaded = findLoadedClass(className) != null ? true : false;
        return hasLoaded;
    }

    /**
     * loadClass
     * @param urls ["file:/D:/a.class", "jar:file:/D:/b.class"]
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadClass(URL[] urls, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(urls);
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{urls});
        Class<?> clazz = classLoader.loadClass(className);
        return clazz;
    }

    /**
     * loadClass
     * @param urls ["file:/D:/a.class", "jar:file:/D:/a.class"]
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadClass(List<URL> urls, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{urls});
        Class<?> clazz = classLoader.loadClass(className);
        return clazz;
    }

    /**
     * loadClass
     * @param url file:/D:/a.class   jar:file:/D:/a.class
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private final static Class<?> loadClass(URL url, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> clazz = classLoader.loadClass(className);
        return clazz;
    }

    /**
     * loadClass
     * @param url file:/D:/a.class   jar:file:/D:/a.class
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadClass(URL url) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        String className = ResourceSugar.convertURLToClassName(url.toString());
        Class<?> clazz = classLoader.loadClass(className);
        return clazz;
    }

    public final static Class<?> load(String className, boolean initialize, ClassLoader classLoader) {
        try {
            return Class.forName(className, initialize, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public final static Class<?> load(String className, boolean initialize) {
        return load(className, initialize, ClassLoaderSugar.getDefaultClassLoader());
    }

    /**
     * load 加载一个类
     * @param className
     * @return
     */
    public final static Class<?> load(String className) {
        return load(className, true, ClassLoaderSugar.getDefaultClassLoader());
    }

    @Nullable
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;

        try {
            classLoader = Thread.currentThread().getContextClassLoader();
        } catch (Exception e) {
        }
        if (classLoader == null) {
            classLoader = ClassLoaderSugar.class.getClassLoader();
            if (classLoader == null) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Exception e) {
                }
            }
        }

        return classLoader;
    }

}
