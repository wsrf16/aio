package com.aio.portable.swiss.sugar.meta.classloader;

import com.aio.portable.swiss.suite.io.IOSugar;
import com.aio.portable.swiss.suite.io.NIOSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.List;

class ByteCodeClassLoader extends ClassLoader {
//    private static final Log log = LogFactory.getLog(ByteCodeClassLoader.class);
    private static final LocalLog log = LocalLog.getLog(ByteCodeClassLoader.class);

    private native Class<?> findBootstrapClass(String name);

    private Class<?> findBootstrapClassOrNull(String name) {
        return findBootstrapClass(name);
    }

    private ClassLoader parent;

    private String path;

//    private static final URL[] toURLs(String[] files) {
//        URL[] urls = new URL[files.length];
//        for (int i = 0; i < files.length; i++) {
//            try {
//                urls[i] = new URL(files[i]);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }
//        return urls;
//    }
//
//    private static final URL[] toURL(String file) {
//        URL[] urls = new URL[1];
//        try {
//            urls[0] = new URL(file);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//        return urls;
//    }

    public ByteCodeClassLoader(String path) {
        this(path, getSystemClassLoader());
        this.parent = getSystemClassLoader();
    }

    public ByteCodeClassLoader(String path, ClassLoader parent) {
        super(parent);
        this.path = path;
    }

    @Override
    public final Class<?> loadClass(String name) {
        // First check if we have permission to access the package. This
        // should go away once we've added support for exported packages.
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            int i = name.lastIndexOf('.');
            if (i != -1) {
                sm.checkPackageAccess(name.substring(0, i));
            }
        }

        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
//                long t0 = System.nanoTime();
                try {
                    if (parent != null) {
                        c = parent.loadClass(name);
                    } else {
                        c = findBootstrapClassOrNull(name);
                    }
                } catch (ClassNotFoundException e) {
                    // ClassNotFoundException thrown if class not found
                    // from the non-null parent class loader
                }

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
//            if (resolve) {
//                resolveClass(c);
//            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        List<Path> paths = NIOSugar.Files.listChildrenFilePath(path, ".class");
        for (Path path : paths) {
            byte[] bytes;
            String filePath = path.toAbsolutePath().toString();
            try {
                bytes = IOSugar.Streams.toByteArray(filePath);
            } catch (Exception e) {
                if (!(e.getCause() instanceof FileNotFoundException))
                    e.printStackTrace();
                continue;
            }
            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
//            logger.info(filePath + " reload：" + Arrays.asList(ReflectionUtils.getDeclaredFields(clazz)));
            log.info(filePath + " reload：" + clazz.getTypeName() + " " + clazz.hashCode());
            return clazz;
        }
        return null;
    }

}
