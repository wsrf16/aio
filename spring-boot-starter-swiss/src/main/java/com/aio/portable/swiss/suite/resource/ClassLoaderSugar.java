package com.aio.portable.swiss.suite.resource;


import com.aio.portable.swiss.sugar.StringSugar;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.List;

public abstract class ClassLoaderSugar {

    /**
     * 判断某一个类是否已加载（被实例化过，仅声明不会被加载）
     *
     * @param classLoader
     * @param className
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public final static Class<?> findLoadedClass(ClassLoader classLoader, String className) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = classLoader.getClass().getDeclaredMethod("findLoadedClass", new Class[]{String.class});
        ReflectionUtils.makeAccessible(method);

        Class<?> clazz = (Class<?>) method.invoke(classLoader, className);
        return clazz;
    }

    /**
     * 判断某一个类是否已加载（被实例化过，仅声明不会被加载）
     *
     * @param classLoader
     * @param className
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public final static boolean hasLoaded(ClassLoader classLoader, String className) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        boolean hasLoaded = findLoadedClass(classLoader, className) != null ? true : false;
        return hasLoaded;
    }

    /**
     * 判断在某一线程中，某一个类是否已加载（被实例化过，仅声明不会被加载）
     *
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public final static boolean hasLoadedInCurrentThread(String className) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        return hasLoaded(Thread.currentThread().getContextClassLoader(), className);
    }

    /**
     * load 加载一个类
     *
     * @param className
     * @return
     */
    public final static boolean load(String className) {
        boolean b = false;
        try {
            Class.forName(className);
            b = true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            b = false;
        }
        return b;
    }


    /**
     * loadClass
     * @param urls ["file:/D:/a.class", "jar:file:/D:/b.class"]
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadedClass(URL[] urls, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(urls);
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{urls});
        Class<?> aClass = classLoader.loadClass(className);
        return aClass;
    }

    /**
     * loadClass
     * @param urls ["file:/D:/a.class", "jar:file:/D:/a.class"]
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadedClass(List<URL> urls, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(urls.toArray(new URL[urls.size()]));
//        URLClassLoader classLoader = new URLClassLoader(new URL[]{urls});
        Class<?> aClass = classLoader.loadClass(className);
        return aClass;
    }

    /**
     * loadClass
     * @param url file:/D:/a.class   jar:file:/D:/a.class
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    private final static Class<?> loadedClass(URL url, String className) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        Class<?> aClass = classLoader.loadClass(className);
        return aClass;
    }

    /**
     * loadClass
     * @param url file:/D:/a.class   jar:file:/D:/a.class
     * @return
     * @throws ClassNotFoundException
     */
    public final static Class<?> loadedClass(URL url) throws ClassNotFoundException {
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        String className = ResourceSugar.toCompleteClassName(url.toString());
//        className = StringSugar.removeStart(className, ".");
        Class<?> aClass = classLoader.loadClass(className);
        return aClass;
    }
}
