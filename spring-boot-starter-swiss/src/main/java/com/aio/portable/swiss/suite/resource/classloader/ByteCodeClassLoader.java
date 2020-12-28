package com.aio.portable.swiss.suite.resource.classloader;

import com.aio.portable.swiss.suite.io.IOSugar;
import com.aio.portable.swiss.suite.io.NIOSugar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

class ByteCodeClassLoader extends ClassLoader {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private native Class<?> findBootstrapClass(String name);

    private Class<?> findBootstrapClassOrNull(String name) {
        return findBootstrapClass(name);
    }

    private ClassLoader parent;

    private String path;

//    private final static URL[] toURLs(String[] files) {
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
//    private final static URL[] toURL(String file) {
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

//    public HotClassLoader(String[] files) {
//        super();
//    }

//    public HotClassLoader(String[] files, ClassLoader parent) {
//        super(toURLs(files), parent);
//    }

//    public HotClassLoader(String[] files, ClassLoader parent,
//                          URLStreamHandlerFactory factory) {
//        super(toURLs(files), parent, factory);
//    }

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
            } catch (IOException e) {
                if (!(e instanceof FileNotFoundException))
                    e.printStackTrace();
                continue;
            }
            Class<?> clazz = this.defineClass(name, bytes, 0, bytes.length);
//            logger.info(filePath + " reload：" + Arrays.asList(ReflectionUtils.getDeclaredFields(clazz)));
            logger.info(filePath + " reload：" + clazz.getTypeName() + " " + clazz.hashCode());
            return clazz;
        }
        return null;
    }

}
