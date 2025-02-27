package com.aio.portable.swiss.sugar.meta.classloader;

import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class HotURLClassLoader extends URLClassLoader {

    public static final HotURLClassLoader newInstance(String file) {
        return newInstance(new File(file));
    }

    public static final HotURLClassLoader newInstance(File file) {
        return newInstance(URLSugar.toURL(file));
    }

    public static final HotURLClassLoader newInstance(URL url) {
        return new HotURLClassLoader(url);
    }

    protected HotURLClassLoader(URL url) {
        super(new URL[]{url});
    }

    protected HotURLClassLoader(URL[] urls) {
        super(urls);
    }

    protected HotURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    protected HotURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }


    public final void addURL(URL url) {
        ClassLoaderSugar.addURL(this, url);
    }

    private Class<?> findBootstrapClassOrNull(String name)    {
        return ClassSugar.Methods.invoke(this, "findBootstrapClassOrNull", name);
    }

    protected Class<?> loadParentClass(String name) {
        Class<?> c = null;
        try {
            if (getParent() != null) {
                c = getParent().loadClass(name);
            } else {
                c = findBootstrapClassOrNull(name);
            }
        } catch (ClassNotFoundException e) {
            // ClassNotFoundException thrown if class not found
            // from the non-null parent class loader
        }
        return c;
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
//                long t0 = System.nanoTime();

                c = loadParentClass(name);

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
//                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
//                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
//                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}