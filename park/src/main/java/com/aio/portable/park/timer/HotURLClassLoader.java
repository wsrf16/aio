package com.aio.portable.park.timer;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.HashMap;
import java.util.Map;

// LaunchedURLClassLoader
public class HotURLClassLoader extends URLClassLoader {
    private native Class<?> findBootstrapClass(String name);

    private Class<?> findBootstrapClassOrNull(String name) {
        return findBootstrapClass(name);
    }

    private static final URL[] toURLs(String[] files) {
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            try {
                urls[i] = new URL(files[i]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return urls;
    }

    private static final URL[] toURL(String file) {
        URL[] urls = new URL[1];
        try {
            urls[0] = new URL(file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return urls;
    }

    public HotURLClassLoader(String file) {
        super(toURL(file));
//        File file1;
//        try {
//            file1 = new File(new URL(file).toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }


//        WatchServiceThread watchServiceThread = new WatchServiceThread(Paths.get(file1.toString()));
//        watchServiceThread.setModifyHandler(f -> {
//            System.out.println("文件改动：" + f.getPath());
//            loadedClass.keySet().forEach(name -> {
//                try {
//                    Class<?> clazz = loadClass(name);
//                    Map<String, Class> stringClassMap = BeanSugar.PropertyDescriptors.toNameClassMap(clazz);
//                    System.out.println("重新加载：" + stringClassMap);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException(e);
//                }
//            });
//        });
//        watchServiceThread.listen();
    }

    public HotURLClassLoader(String[] files) {
        super(toURLs(files));
    }

    public HotURLClassLoader(String[] files, ClassLoader parent) {
        super(toURLs(files), parent);
    }

    public HotURLClassLoader(String[] files, ClassLoader parent,
                             URLStreamHandlerFactory factory) {
        super(toURLs(files), parent, factory);
    }

//    @Override
//    public final Class<?> loadClass(String name, boolean resolve)
//            throws ClassNotFoundException
//    {
//        // First check if we have permission to access the package. This
//        // should go away once we've added support for exported packages.
//        SecurityManager sm = System.getSecurityManager();
//        if (sm != null) {
//            int i = name.lastIndexOf('.');
//            if (i != -1) {
//                sm.checkPackageAccess(name.substring(0, i));
//            }
//        }
//
//        synchronized (getClassLoadingLock(name)) {
//            // First, check if the class has already been loaded
//            Class<?> c = findLoadedClass(name);
//            if (c == null) {
//                long t0 = System.nanoTime();
//                try {
//                    if (parent != null) {
//                        c = parent.loadClass(name, false);
//                    } else {
//                        c = findBootstrapClassOrNull(name);
//                    }
//                } catch (ClassNotFoundException e) {
//                    // ClassNotFoundException thrown if class not found
//                    // from the non-null parent class loader
//                }
//
//                if (c == null) {
//                    // If still not found, then invoke findClass in order
//                    // to find the class.
//                    long t1 = System.nanoTime();
//                    c = findClass(name);
//
//                    // this is the defining class loader; record the stats
//                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
//                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
//                    sun.misc.PerfCounter.getFindClasses().increment();
//                }
//            }
//            if (resolve) {
//                resolveClass(c);
//            }
//            return c;
//        }
//    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = super.findClass(name);
        loadedClass.put(name, clazz);
        return clazz;
    }

    Map<String, Class<?>> loadedClass = new HashMap<>();
}
