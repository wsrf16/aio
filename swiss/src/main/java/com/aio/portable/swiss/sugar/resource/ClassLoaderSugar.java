package com.aio.portable.swiss.sugar.resource;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

public abstract class ClassLoaderSugar {

    /**
     * 判断某一个类是否已加载（被实例化过，仅声明不会被加载）
     *
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     */
    public static boolean hasLoaded(String className, ClassLoader classLoader) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        final String classLoaderClassName = "java.lang.ClassLoader";
        Class<?> cl = Class.forName(classLoaderClassName, false, Thread.currentThread().getContextClassLoader());
        Method method = cl.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
        method.setAccessible(true);

        boolean hasLoaded = false;

        if (method.invoke(classLoader, className) != null) {
            hasLoaded = true;
        } else {
            hasLoaded = false;
        }

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
    public static boolean hasLoadedInCurrentThread(String className) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        return hasLoaded(className, Thread.currentThread().getContextClassLoader());
    }

    /**
     * load 加载一个类
     *
     * @param className
     * @return
     */
    public static boolean load(String className) {
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



}
