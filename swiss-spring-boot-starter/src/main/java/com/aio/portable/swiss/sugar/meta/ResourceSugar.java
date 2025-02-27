package com.aio.portable.swiss.sugar.meta;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.ProtocolType;
import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.io.IOSugar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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
//    org.springframework.core.io.ResourceLoader
//    org.springframework.util.ResourceUtils
    /**
     * JarPath         eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * Path            eg. /com/art/Book.class
     * URL             eg. jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class
     * 未打成JAR时目录  eg. file:/D:/Project/art/target/classes
     * 打成JAR时目录    eg. file:/D:/Project/art/art-1.0-SNAPSHOT.jar
     * 未打成JAR时文件  eg. file:/D:/Project/art/target/classes/com/art/Book.class
     * 打成JAR时文件    eg. file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class
     */

    /**
     * spellJarResourceURL 从Jar中根据资源路径拼出URL
     * @param path              eg. "/com/art/Book.class"
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public static final URL spellResourceURLInJar(final String path, final String jarPath) {
        URL jarURL = URLSugar.toFileURL(jarPath);
//            String resourceInJarChanged = resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath;
        String resourceInJarChanged = StringSugar.wrapStartIfNotExists(path, "/");
        String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJarChanged);
        URL url = URLSugar.toURL(pathOfURL);
        return url;
    }

    /**
     * getResourceURLInJar 从Jar中获得资源
     * @param path              eg. "/com/art/Book.class"
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     */
    public static final URL getJarResource(final String path, final String jarPath) {
        URL url = getJarResourceList(jarPath).stream().filter(c -> c.getPath().endsWith(path)).findFirst().get();
        return url;
    }

//    /**
//     * getResourceInJarAsStream 从Jar中获得资源流
//     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
//     * @param resourcePath      eg. "/com/art/Book.class"
//     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
//     */
//    public static final InputStream getResourceInJarAsStream(final String jarPath, final String resourcePath) throws IOException {
//        return spellJarResourceURL(jarPath, resourcePath).openStream();
//    }

    /**
     * getJarResourceList 从Jar中获得所有资源集合
     * @param jarPath eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return
     */
    public static final List<URL> getJarResourceList(final String jarPath) {
        JarFile jarFile = toJarFile(jarPath);
        return getJarResourceList(jarFile);
    }

    /**
     * getJarResourceList 从Jar中获得所有资源集合
     * @param jarFile eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return
     */
    public static final List<URL> getJarResourceList(final JarFile jarFile) {
        List<JarEntry> jarEntryList = jarFile.stream().collect(Collectors.toList());
        String jarSelfURL = URLSugar.toFileURL(jarFile.getName()).toExternalForm();
        List<URL> urlList = jarEntryList.stream().map(c -> {
            String resourcePath = c.getName();
            String jarURLPath = MessageFormat.format("{0}:{1}!/{2}", ProtocolType.jar, jarSelfURL, resourcePath);
            URL url = URLSugar.toURL(jarURLPath);
            return url;
        }).collect(Collectors.toList());
        return urlList;
    }

    private static JarFile toJarFile(String jarPath) {
        try {
            return new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getClassLoaderResourceList 利用ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
     *
     * @param path                 eg. "com/art/Book.class"
     * @param classLoader          eg. Thread.currentThread().getContextClassLoader()
     * @return eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/sandbox/console/Book.class"
     */
    public static final List<URL> getClassLoaderResourceList(String path, ClassLoader classLoader) {
        try {
            Enumeration<URL> urlEnumeration = classLoader.getResources(path);
            List<URL> urlList = Collections.list(urlEnumeration);
            return urlList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getClassLoaderResourceList 利用当前ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
     *
     * @param path             eg. "com/art/Book.class"
     * @return                 eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/com/aio/portable/swiss/sandbox/Wood.class"
     */
    public static final List<URL> getClassLoaderResourceList(String path) {
        ClassLoader classLoader = ClassLoaderSugar.getDefaultClassLoader();
        return getClassLoaderResourceList(path, classLoader);
    }

    /**
     * getClassLoaderResourceListByClass
     * @param clazz eg. Book
     * @return
     */
    public static final List<URL> getClassLoaderResourceListByClass(final Class<?> clazz) {
        return getClassLoaderResourceListByClassName(clazz.getTypeName());
    }

    /**
     * getClassLoaderResourceListByClassName
     * @param className eg. com.art.Book
     * @return
     */
    public static final List<URL> getClassLoaderResourceListByClassName(final String className) {
        String resourceRelationPath = ClassSugar.convertClassNameToResourcePath(className);
        return getClassLoaderResourceList(resourceRelationPath);
    }

    /**
     * existClassLoaderResource
     * @param path eg. "com/art/Book.class"
     * @param classLoader
     * @return
     */
    public static final boolean existClassLoaderResource(String path, ClassLoader classLoader) {
        List<URL> urlList = getClassLoaderResourceList(path, classLoader);
        boolean exist = urlList != null && urlList.size() > 0;
        return exist;
    }

    /**
     * existClassLoaderResource
     * @param path eg. "com/art/Book.class"
     * @return
     */
    public static final boolean existClassLoaderResource(String path) {
        List<URL> urlList = getClassLoaderResourceList(path);
        boolean exist = urlList != null && urlList.size() > 0;
        return exist;
    }

    /**
     * getResourceAsStream
     * @param path
     * @param jarFile
     * @return
     */
    public static final InputStream getResourceAsStream(String path, String jarFile) {
        ClassLoader classLoader = new URLClassLoader(new URL[]{URLSugar.toFileURL(jarFile)});
        return ResourceSugar.getResourceAsStream(path, classLoader);
    }

    /**
     * getResourceAsStream
     * @param path
     * @param jarFile
     * @return
     */
    public static final InputStream getResourceAsStream(String path, File jarFile) {
        ClassLoader classLoader = new URLClassLoader(new URL[]{URLSugar.toURL(jarFile)});
        return ResourceSugar.getResourceAsStream(path, classLoader);
    }

    /**
     * getResourceAsStream
     * @param path
     * @param classLoader
     * @return
     */
    public static final InputStream getResourceAsStream(String path, ClassLoader classLoader) {
        return classLoader.getResourceAsStream(path);
    }

    /**
     * getResourceAsStream
     * @param path
     * @return
     */
    public static final InputStream getResourceAsStream(String path) {
        return getResourceAsStream(path, Thread.currentThread().getContextClassLoader());
    }

    /**
     * getResourceAsString
     * @param path
     * @param jarFile
     * @return
     */
    public static final String getResourceAsString(String path, String jarFile) {
        ClassLoader classLoader = new URLClassLoader(new URL[]{URLSugar.toFileURL(jarFile)});
        return ResourceSugar.getResourceAsString(path, classLoader);
    }

    /**
     * getResourceAsString
     * @param path
     * @param jarFile
     * @return
     */
    public static final String getResourceAsString(String path, File jarFile) {
        ClassLoader classLoader = new URLClassLoader(new URL[]{URLSugar.toURL(jarFile)});
        return ResourceSugar.getResourceAsString(path, classLoader);
    }

    /**
     * getResourceAsString
     * @param path
     * @param classLoader
     * @return
     */
    public static final String getResourceAsString(String path, ClassLoader classLoader) {
        InputStream inputStream = classLoader.getResourceAsStream(path);
        return IOSugar.Streams.toString(inputStream);
    }

    /**
     * getResourceAsString
     * @param path
     * @return
     */
    public static final String getResourceAsString(String path) {
        InputStream inputStream = getResourceAsStream(path, Thread.currentThread().getContextClassLoader());
        return IOSugar.Streams.toString(inputStream);
    }


    private static final String[] delimiters = new String[]{"//", "/"};
    private static final String delimiter = "/";

    /**
     * concat 连接多个资源路径
     * @param paths
     * @return
     */
    public static final String concat(String... paths) {
        Stream<String> fixPartStream = Arrays.stream(paths).map(c -> StringSugar.replaceEach(c, delimiters, new String[]{delimiter, delimiter}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> StringSugar.trim(c, delimiter)).toArray(String[]::new);
        String combined = String.join(delimiter, fixParts);
        String start = fixPartList.get(0).startsWith(delimiter) ? delimiter : Constant.EMPTY;
        String end = fixPartList.get(paths.length - 1).endsWith(delimiter) ? delimiter : Constant.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

//    public static final void ff() throws FileNotFoundException {
//        org.springframework.util.ResourceUtils.getFile(
//                org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX + "static/excel/userTemplate.xlsx");
//    }

    /**
     * convertPathToClassName
     * @param path className/packageName eg. com/company/biz | com/company/biz/Book.class
     * @return com.company.biz | com.company.biz.Book
     */
    public static final String convertResourcePathToClassName(String path) {
        String temp = StringSugar.trim(path, "/", ".class");
        temp = temp.replace("/", ".");
        return temp;
    }

    /**
     * convertURLToClassName
     * @param url jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
     * @return
     */
    public static final String convertJarURLToClassName(String url) {
        // jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        // ->
        // /BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        String resourceRelative = url.contains("!") ? url.split("!")[1] : url;
        // -> com/aio/portable/park/config/BeanConfig.class
        String classFilePath = resourceRelative.contains("classes/") ? resourceRelative.split("classes/")[1] : resourceRelative;
        // -> com.aio.portable.park.config.BeanConfig
        String className = convertResourcePathToClassName(classFilePath);
        return className;
    }

//    public static final URL getURL(String resourcePath) {
////        new File(ResourceUtils.getURL("classpath:\\WorkFlow.txt").getPath())
//        try {
//            return ResourceUtils.getURL(resourcePath);
//        } catch (FileNotFoundException e) {
////            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


}