package com.aio.portable.swiss.sugar.resource;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.ProtocolType;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.io.IOSugar;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ResourceSugar {

    /**
     * JarPath         eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * ResourcePath    eg. /com/art/Book.class
     * URL             eg. jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class
     * 未打成JAR时目录  eg. file:/D:/Project/art/target/classes
     * 打成JAR时目录    eg. file:/D:/Project/art/art-1.0-SNAPSHOT.jar
     * 未打成JAR时文件  eg. file:/D:/Project/art/target/classes/com/art/Book.class
     * 打成JAR时文件    eg. file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class
     */

    /**
     * spellJarResourceURL 从Jar中根据资源路径拼出URL
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceLocation  eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public static final URL spellJarResourceURL(final String jarPath, final String resourceLocation) {
        try {
            URL jarURL = new File(jarPath).toURI().toURL();
            String resourceInJarChanged = resourceLocation.startsWith("/") ? resourceLocation : "/" + resourceLocation;
            String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJarChanged);
            URL url = new URL(pathOfURL);
            return url;
        } catch (MalformedURLException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * getURLInJar 从Jar中获得资源
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceLocation  eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     */
    public static final URL getJarResourceURL(final String jarPath, final String resourceLocation) {
        URL url = getAllJarResourceURLs(jarPath).stream().filter(c -> c.getPath().endsWith(resourceLocation)).findFirst().get();
        return url;
    }

//    /**
//     * getResourceInJarAsStream 从Jar中获得资源流
//     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
//     * @param resourceLocation  eg. "/com/art/Book.class"
//     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
//     */
//    public static final InputStream getResourceInJarAsStream(final String jarPath, final String resourceLocation) throws IOException {
//        return spellJarResourceURL(jarPath, resourceLocation).openStream();
//    }

    /**
     * getAllJarResourceURLs 从Jar中获得所有资源集合
     * @param jarPath eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return
     */
    public static final List<URL> getAllJarResourceURLs(final String jarPath) {
        try {
            URL jarURL = new File(jarPath).toURI().toURL();
            List<JarEntry> jarEntryList = new JarFile(jarPath).stream().collect(Collectors.toList());
            List<URL> urlList = jarEntryList.stream().map(c -> {
                String resourceInJar = "/" + c.getName();
                try {
                    String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJar);
                    return new URL(pathOfURL);
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
            return urlList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private static final String[] delimiters = new String[]{"//", "/"};
    private static final String delimiter = "/";

    /**
     * concat 连接多个资源路径
     * @param locations
     * @return
     */
    public static final String concat(String... locations) {
        Stream<String> fixPartStream = Arrays.stream(locations).map(c -> StringSugar.replaceEach(c, delimiters, new String[]{delimiter, delimiter}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> StringSugar.trim(c, delimiter)).toArray(String[]::new);
        String combined = String.join(delimiter, fixParts);
        String start = fixPartList.get(0).startsWith(delimiter) ? delimiter : Constant.EMPTY;
        String end = fixPartList.get(locations.length - 1).endsWith(delimiter) ? delimiter : Constant.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

//    public static final void ff() throws FileNotFoundException {
//        org.springframework.util.ResourceUtils.getFile(
//                org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX + "static/excel/userTemplate.xlsx");
//    }

    /**
     * convertLocationToClassName
     * @param location className/packageName eg. com/company/biz | com/company/biz/Book.class
     * @return com.company.biz | com.company.biz.Book
     */
    public static final String convertResourceLocationToClassName(String location) {
        String temp = StringSugar.trim(location, "/", ".class");
        temp = temp.replace("/", ".");
        return temp;
    }

    /**
     * convertURLToClassName
     * @param url jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
     * @return
     */
    public static final String convertURLToClassName(String url) {
        // jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        // ->
        // /BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        String resourceRelative = url.contains("!") ? url.split("!")[1] : url;
        // -> com/aio/portable/park/config/BeanConfig.class
        String classFilePath = resourceRelative.contains("classes/") ? resourceRelative.split("classes/")[1] : resourceRelative;
        // -> com.aio.portable.park.config.BeanConfig
        String className = convertResourceLocationToClassName(classFilePath);
        return className;
    }

//    public static final URL getURL(String resourceLocation) {
////        new File(ResourceUtils.getURL("classpath:\\WorkFlow.txt").getPath())
//        try {
//            return ResourceUtils.getURL(resourceLocation);
//        } catch (FileNotFoundException e) {
////            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public abstract static class ByClass {
        public static final URL getResourceURL(Class clazz, String resourceRelationPath) {
            return clazz.getResource(resourceRelationPath);
        }

        /**
         * getCodeSourceLocation
         * 如果直接执行.class文件那么会得到当前class的绝对路径。
         * 如果封装在jar包里面执行jar包那么会得到当前jar包的绝对路径。
         * @param clazz
         * @return
         */
        public static final URL getCodeSourceLocation(Class clazz) {
            URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
            return location;
        }

        /**
         * getResourceAsStream
         * @param clazz
         * @param name
         * @return
         */
        public static final InputStream getResourceAsStream(Class<?> clazz, String name) {
            return clazz.getResourceAsStream(name);
        }

        /**
         * getResourceAsStream
         * @param name
         * @return
         */
        public static final InputStream getResourceAsStream(String name) {
            return getResourceAsStream(ResourceSugar.class, name);
        }

        /**
         * getResourceAsString
         * @param clazz
         * @param name
         * @return
         */
        public static final String getResourceAsString(Class<?> clazz, String name) {
            InputStream inputStream = clazz.getResourceAsStream(name);
            return IOSugar.Streams.toString(inputStream);
        }

        /**
         * getResourceAsString
         * @param name
         * @return
         */
        public static final String getResourceAsString(String name) {
            InputStream inputStream = getResourceAsStream(ResourceSugar.class, name);
            return IOSugar.Streams.toString(inputStream);
        }
    }

    public abstract static class ByClassLoader {
        /**
         * getResources 利用ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
         *
         * @param classLoader          eg. Thread.currentThread().getContextClassLoader()
         * @param resourceLocation     eg. "com/art/Book.class"
         * @return eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/sandbox/console/Book.class"
         */
        public static final List<URL> getResourceURLs(ClassLoader classLoader, String resourceLocation) {
            try {
                Enumeration<URL> urlEnumeration = classLoader.getResources(resourceLocation);
                List<URL> urlList = Collections.list(urlEnumeration);
                return urlList;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * getResources 利用当前ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
         *
         * @param resourceLocation eg. "com/art/Book.class"
         * @return                 eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/com/aio/portable/swiss/sandbox/Wood.class"
         */
        public static final List<URL> getResourceURLs(String resourceLocation) {
            ClassLoader classLoader = ClassLoaderSugar.getDefaultClassLoader();
            return getResourceURLs(classLoader, resourceLocation);
        }

        /**
         * existResource
         * @param resourceLocation eg. "com/art/Book.class"
         * @return
         */
        public static final boolean existResource(String resourceLocation) {
            List<URL> urlList = ByClassLoader.getResourceURLs(resourceLocation);
            boolean exist = urlList != null && urlList.size() > 0;
            return exist;
        }

        /**
         * getResourcesByClass
         * @param clazz eg. Book
         * @return
         */
        public static final List<URL> getClassURLs(final Class<?> clazz) {
            return getClassURLs(clazz.getTypeName());
        }

        /**
         * getResourcesByClass
         * @param className eg. com.art.Book
         * @return
         */
        public static final List<URL> getClassURLs(final String className) {
            String resourceRelationPath = ClassSugar.convertClassNameToResourceLocation(className);
            return getResourceURLs(resourceRelationPath);
        }

        /**
         * getResourceAsStream
         * @param classLoader
         * @param path
         * @return
         */
        public static final InputStream getResourceAsStream(ClassLoader classLoader, String path) {
            return classLoader.getResourceAsStream(path);
        }

        /**
         * getResourceAsStream
         * @param path
         * @return
         */
        public static final InputStream getResourceAsStream(String path) {
            return getResourceAsStream(Thread.currentThread().getContextClassLoader(), path);
        }

        /**
         * getResourceAsString
         * @param classLoader
         * @param path
         * @return
         */
        public static final String getResourceAsString(ClassLoader classLoader, String path) {
            InputStream inputStream = classLoader.getResourceAsStream(path);
            return IOSugar.Streams.toString(inputStream);
        }

        /**
         * getResourceAsString
         * @param path
         * @return
         */
        public static final String getResourceAsString(String path) {
            InputStream inputStream = getResourceAsStream(Thread.currentThread().getContextClassLoader(), path);
            return IOSugar.Streams.toString(inputStream);
        }
    }


}