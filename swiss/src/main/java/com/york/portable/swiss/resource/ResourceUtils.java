package com.york.portable.swiss.resource;

import com.york.portable.swiss.global.ProtocolType;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
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

public class ResourceUtils {

    /**
     * spellResourceInJar 从Jar中根据资源路径拼出URL
     *
     * @param jarPath       eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceInJar eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public static URL spellResourceInJar(final String jarPath, final String resourceInJar) throws MalformedURLException {
        File jarFile = new File(jarPath);
        URL jarURL = jarFile.toURI().toURL();
        String resourceInJarChanged = resourceInJar.startsWith("/") ? resourceInJar : "/" + resourceInJar;
        String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJarChanged);
        URL url = new URL(pathOfURL);
        return url;
    }

    /**
     * getURLInJar 从Jar中获得资源
     *
     * @param jarPath       eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceInJar eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public static URL getResourceInJar(final String jarPath, final String resourceInJar) throws IOException {
        URL url = getResourcesInJar(jarPath).stream().filter(c -> c.getPath().endsWith(resourceInJar)).findFirst().get();
        return url;
    }

    /**
     * getResourceInJarAsStream 从Jar中获得资源流
     *
     * @param jarPath       eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceInJar eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public static InputStream getResourceInJarAsStream(final String jarPath, final String resourceInJar) throws IOException {
        return spellResourceInJar(jarPath, resourceInJar).openStream();
    }

    /**
     * getResourcesInJar 从Jar中获得所有资源集合
     *
     * @param jarPath eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return
     * @throws IOException
     */
    public static List<URL> getResourcesInJar(final String jarPath) throws IOException {
        File file = new File(jarPath);
        URL jarURL = file.toURI().toURL();

        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        List<JarEntry> jarEntryList = Collections.list(jarEntryEnumeration);
        List<URL> urlList = jarEntryList.stream().map(c -> {
            String resourceInJar = "/" + c.getName();
            try {
                String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJar);
                return new URL(pathOfURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return urlList;
    }

    /**
     * getResourceByClassLoader 利用ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
     *
     * @param classLoader          eg. Thread.currentThread().getContextClassLoader()
     * @param resourceRelationPath eg. "/com/art/Book.class"
     * @return eg. "file:/D:/Project/swiss/target/classes/com/york/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/sandbox/console/Book.class"
     * @throws IOException
     */
    public static List<URL> getResourcesInClassFile(ClassLoader classLoader, String resourceRelationPath) throws IOException {
        Enumeration<URL> urlEnumeration = classLoader.getResources(resourceRelationPath);
        List<URL> urlList = Collections.list(urlEnumeration);
        return urlList;
    }

    /**
     * getResourcesByClassLoaderInCurrentThread 利用当前ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
     *
     * @param resourceRelationPath eg. "/com/art/Book.class"
     * @return eg. "file:/D:/Project/swiss/target/classes/com/york/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/com/york/portable/swiss/sandbox/Wood.class"
     * @throws IOException
     */
    public static List<URL> getResourcesInClassFile(String resourceRelationPath) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return getResourcesInClassFile(classLoader, resourceRelationPath);
    }

    /**
     * getResourcesByClass
     *
     * @param clazz eg. Book
     * @return
     * @throws IOException
     */
    public static List<URL> getResourcesByClass(final Class clazz) throws IOException {
        return getResourcesByClassName(clazz.getName());
    }

    /**
     * getResourcesByClassName
     *
     * @param className eg. com.art.Book
     * @return
     * @throws IOException
     */
    public static List<URL> getResourcesByClassName(final String className) throws IOException {
        final String resourceRelationPath = ResourceUtils.fullName2Path(className);
        return ResourceUtils.getResourcesInClassFile(resourceRelationPath);
    }

    /**
     * fullName2Name
     *
     * @param fullName className/packageName eg. com.company.biz | com.company.biz.Book
     * @return com/company/biz | com/company/biz/Book
     */
    public static String fullName2Path(String fullName) {
        String path;
//        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(fullName).concat(".class");
        path = fullName.replace(".", "/").concat(".class");
        return path;
    }

    /**
     * path2FullName
     *
     * @param path className/packageName eg. com/company/biz | com/company/biz/Book
     * @return com.company.biz | com.company.biz.Book
     */
    public static String path2FullName(String path) {
        path = StringUtils.removeEnd(path, ".class");
        String fullName = path.replace("/", ".");
        fullName = StringUtils.removeStart(fullName, ".");
        return fullName;
    }

    final static String[] intervals = new String[]{"//", "/"};
    final static String interval = "/";

    /**
     * concat 连接多个资源路径
     * @param parts
     * @return
     */
    public static String concat(String... parts) {
        Stream<String> fixPartStream = Arrays.stream(parts).map(c -> StringUtils.replaceEach(c, intervals, new String[]{interval, interval}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            String _a = StringUtils.removeStart(c, interval);
            String _b = StringUtils.removeEnd(_a, interval);
            return _b;
        }).toArray(String[]::new);
        String combined = String.join(interval, fixParts);
        String start = fixPartList.get(0).startsWith(interval) ? interval : StringUtils.EMPTY;
        String end = fixPartList.get(parts.length - 1).endsWith(interval) ? interval : StringUtils.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

//    public static void ff() throws FileNotFoundException {
//        org.springframework.util.ResourceUtils.getFile(
//                org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX + "static/excel/userTemplate.xlsx");
//    }
}
