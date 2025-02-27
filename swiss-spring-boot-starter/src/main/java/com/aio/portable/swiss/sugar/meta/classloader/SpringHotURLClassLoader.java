package com.aio.portable.swiss.sugar.meta.classloader;

import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class SpringHotURLClassLoader extends URLClassLoader {

    public static final SpringHotURLClassLoader newInstance(String file) {
        return newInstance(new File(file));
    }

    public static final SpringHotURLClassLoader newInstance(File file) {
        return newInstance(URLSugar.toURL(file));
    }

    public static final SpringHotURLClassLoader newInstance(URL url) {
        return new SpringHotURLClassLoader(url);
    }

    protected SpringHotURLClassLoader(URL url) {
        super(new URL[]{url});
    }

    protected SpringHotURLClassLoader(URL[] urls) {
        super(urls);
    }

    protected SpringHotURLClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    protected SpringHotURLClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }


    public final void addURL(URL url) {
        ClassLoaderSugar.addURL(this, url);
    }

    private Class<?> findBootstrapClassOrNull(String name) {
        return ClassSugar.Methods.invoke(this, "findBootstrapClassOrNull", name);
    }

//    @Override
//    public final ClassLoader getParent() {
//
//    }

    protected Class<?> loadSpringParentClass(String name) {
        Class<?> c = null;
        try {
//            ClassLoader springClassLoader = ClassUtils.class.getClassLoader();
            ClassLoader springClassLoader = ClassUtils.getDefaultClassLoader();
            if (springClassLoader != null)
                c = springClassLoader.loadClass(name);
        } catch (ClassNotFoundException e) {
            // ClassNotFoundException thrown if class not found
            // from the non-null parent class loader
        }
        return c;
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
                long t0 = System.nanoTime();

                c = loadParentClass(name);
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();

                    c = loadSpringParentClass(name);
                    if (c == null) {
                        c = findClass(name);

                        // this is the defining class loader; record the stats
                        sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                        sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                        sun.misc.PerfCounter.getFindClasses().increment();
                    }
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }
}
