package com.aio.portable.swiss.sugar.meta;


import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public abstract class ClassLoaderSugar {
    // compile/load/

    public static KeyValuePair<Boolean, DiagnosticCollector> compileFiles(String outputPath, String... javaFiles) {
        return compileFiles(outputPath, Arrays.asList(javaFiles));
    }

    /**
     * compile
     *
     * @param javaFiles eg. d:/book.java
     * @return
     */
    public static KeyValuePair<Boolean, DiagnosticCollector> compileFiles(String outputPath, Iterable<String> javaFiles) {
        DiagnosticCollector diagnostics = new DiagnosticCollector();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
//            List<String> options = StringUtils.isEmpty(outputPath) ? null : Arrays.asList("-d", outputPath);
            List<String> options = null;
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT,  Arrays.asList(new File[] { new File(outputPath) }));
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(javaFiles);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options,
                    null, compilationUnits);
            boolean success = task.call();
            return new KeyValuePair<Boolean, DiagnosticCollector>(success, diagnostics);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyValuePair<Boolean, DiagnosticCollector> compileCode(String outputPath, String className, String code) {
        JavaFileObject javaFileObject = new StringJavaFileObject(className, code);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObject);

        DiagnosticCollector diagnostics = new DiagnosticCollector();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        try (StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null)) {
//            List<String> options = StringUtils.isEmpty(outputPath) ? null : Arrays.asList("-d", outputPath);
            List<String> options = null;
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT,  Arrays.asList(new File[] { new File(outputPath) }));
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options,
                    null, compilationUnits);
            boolean success = task.call();
            return new KeyValuePair<Boolean, DiagnosticCollector>(success, diagnostics);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * exist 判断是否存在某一个类
     *
     * @param className eg. com.art.Book
     * @param classLoader
     * @return
     * @throws IOException
     */
    public static final boolean isPresent(String className, ClassLoader classLoader) {
        String resource = ClassSugar.convertClassNameToResourcePath(className);
        return ResourceSugar.existClassLoaderResource(resource, classLoader);
    }

    /**
     * exist 判断是否存在某一个类
     *
     * @param className eg. com.art.Book
     * @return
     * @throws IOException
     */
    public static final boolean isPresent(String className) {
        String resource = ClassSugar.convertClassNameToResourcePath(className);
        return ResourceSugar.existClassLoaderResource(resource);
    }

    /**
     * findLoadedClass 解析出一个类
     * @param className
     * @param classLoader
     * @return
     */
    public static final Class<?> findLoadedClass(String className, ClassLoader classLoader) {
        try {
            Method method = ClassLoader.class.getDeclaredMethod("findLoadedClass", new Class[]{String.class});
            ReflectionUtils.makeAccessible(method);
            Class<?> clazz = (Class<?>) method.invoke(classLoader, className);
            return clazz;
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Class<?> findLoadedClass(String className) {
        return findLoadedClass(className, ClassLoaderSugar.getDefaultClassLoader());
    }

    /**
     * hasLoaded 判断某一个类是否已加载（指被实例化过，仅声明不会被加载）
     * @param className
     * @param classLoader
     * @return
     */
    public static final boolean hasLoaded(String className, ClassLoader classLoader) {
        boolean hasLoaded = findLoadedClass(className, classLoader) != null ? true : false;
        return hasLoaded;
    }

    /**
     * hasLoaded 判断在某一线程中，某一个类是否已加载（指被实例化过，仅声明不会被加载）
     * @param className
     * @return
     */
    public static final boolean hasLoaded(String className) {
        boolean hasLoaded = findLoadedClass(className) != null ? true : false;
        return hasLoaded;
    }

    public static final URLClassLoader toURLClassLoader(URL[] urls) {
        return new URLClassLoader(urls);
    }

    public static final URLClassLoader toURLClassLoader(List<URL> urlList) {
        URL[] urls = urlList.toArray(new URL[urlList.size()]);
        return new URLClassLoader(urls);
    }

    public static final URLClassLoader toURLClassLoader(URL url) {
        URL[] urls = {url};
        return new URLClassLoader(urls);
    }

    public static final URLClassLoader toURLClassLoader(File file) {
        URL[] urls = {URLSugar.toURL(file)};
        return new URLClassLoader(urls);
    }

    /**
     * loadClass
     * @param className
     * @param urls ["jar:file:/D:/a.jar", "file:D:/classes/"]
     * @return
     */
    public static final Class<?> loadClass(String className, URL[] urls) {
            URLClassLoader classLoader = new URLClassLoader(urls);
            return loadClass(className, classLoader);
    }

    /**
     * loadClass
     * @param className
     * @param urlList ["jar:file:/D:/a.jar", "file:D:/classes/"]
     * @return
     */
    public static final Class<?> loadClass(String className, List<URL> urlList) {
        URL[] urls = urlList.toArray(new URL[urlList.size()]);
        return loadClass(className, urls);
    }

    /**
     * loadClass
     * @param className
     * @param url jar:file:/D:/b.jar    file:D:/classes/
     * @return
     * @throws ClassNotFoundException
     */
    public static final Class<?> loadClass(String className, URL url) {
        URL[] urls = {url};
        return loadClass(className, urls);
    }

    /**
     * loadClass
     * @param className
     * @param file file:/D:/b.jar  D:/classes/
     * @return
     * @throws ClassNotFoundException
     */
    public static final Class<?> loadClass(String className, File file) {
        URL[] urls = {URLSugar.toURL(file)};
        return loadClass(className, urls);
    }

    /**
     * loadClass
     * @param classURL jar:file:/D:/a.jar!/com/abc/b.class
     * @return
     * @throws ClassNotFoundException
     */
    public static final Class<?> loadClass(URL classURL) {
        String className = ResourceSugar.convertJarURLToClassName(classURL.toString());
        String classPath = StringSugar.trimStart(classURL.toExternalForm().split("!")[0], "jar:");
        URL jarURL = URLSugar.toURL(classPath);
        return loadClass(className, jarURL);
    }

    public static final <T> Class<T> loadClass(String className, ClassLoader classLoader, boolean link) {
        return ClassSugar.Methods.invoke(classLoader, ClassLoader.class, "loadClass", new Class[]{String.class, boolean.class}, new Object[]{className, link});
    }

    public static final <T> Class<T> loadClass(String className, ClassLoader classLoader) {
        try {
            return (Class<T>) classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> Class<T> loadClass(String className) {
        try {
            return (Class<T>) getDefaultClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> Class<T> forName(String className, ClassLoader classLoader, boolean initialize) {
        try {
            return (Class<T>) Class.forName(className, initialize, classLoader);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static final <T> Class<T> forName(String className, ClassLoader classLoader) {
        return forName(className, classLoader, true);
    }

    /**
     * load 加载一个类
     * @param className
     * @return
     */
    public static final <T> Class<T> forName(String className) {
        return forName(className, ClassLoaderSugar.getDefaultClassLoader(), true);
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
                    throw new RuntimeException(e);
                }
            }
        }

        return classLoader;
    }

    public static final void addURL(URLClassLoader urlClassLoader, String path) {
        File file = new File(path);
        addURL(urlClassLoader, file);
    }

    public static final void addURL(URLClassLoader urlClassLoader, File file) {
        try {
            URL url = file.toURI().toURL();
            addURL(urlClassLoader, url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static final void addURL(URLClassLoader urlClassLoader, URL url) {
        ClassSugar.Methods.invoke(urlClassLoader, URLClassLoader.class, "addURL", url);
    }

    public static final void clear(ClassLoader classLoader, Class<?> clazz) {
        // 解除类与ClassLoader的关联
        ClassSugar.Methods.invoke(classLoader, ClassLoader.class, "clearAssertionStatus");
        Vector<Class<?>> classes = ClassSugar.Fields.getDeclaredFieldValue(classLoader, "classes");
        classes.remove(clazz);


//        Method clearAssertionStatusMethod = ClassLoader.class.getDeclaredMethod("clearAssertionStatus");
//        clearAssertionStatusMethod.setAccessible(true);
//        clearAssertionStatusMethod.invoke(classLoader);
//
//        // 查找并移除类引用
//        Field classesField = ClassLoader.class.getDeclaredField("classes");
//        classesField.setAccessible(true);
//        Vector<Class<?>> classes = (Vector<Class<?>>) classesField.get(classLoader);
//        classes.remove(clazz);
    }

//    public static final void removeURL(URLClassLoader urlClassLoader, URL url) {
//        ClassSugar.invoke(urlClassLoader, "removeURL", url);
//    }
}
