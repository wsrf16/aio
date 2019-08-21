package com.york.portable.swiss.resource;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;

public class ClassLoaderUtils {

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


    private static class BlahUnit {
        private static void todo() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            String name;
            boolean b;
            name = "java.lang.System";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

            name = "java.sql.Date";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);
            java.sql.Date date = new java.sql.Date(0);
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

            name = "com.york.portable.swiss.sandbox.Wood";
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);
            b = ClassLoaderUtils.hasLoadedInCurrentThread(name);
            printResult(name, b);

        }

        private static void printResult(String name, boolean b) {
            if (b) {
                System.out.println(MessageFormat.format("{0}已经加载!", name));
            } else {
                System.out.println(MessageFormat.format("{0}尚未加载!", name));
            }
        }
    }

}
