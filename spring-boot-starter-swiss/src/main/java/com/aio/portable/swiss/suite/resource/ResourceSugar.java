package com.aio.portable.swiss.suite.resource;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.ProtocolType;
import com.aio.portable.swiss.sugar.StringSugar;

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
     * spellResourceInJar 从Jar中根据资源路径拼出URL
     *
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceLocation  eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public final static URL spellResourceInJar(final String jarPath, final String resourceLocation) throws MalformedURLException {
        File jarFile = new File(jarPath);
        URL jarURL = jarFile.toURI().toURL();
        String resourceInJarChanged = resourceLocation.startsWith("/") ? resourceLocation : "/" + resourceLocation;
        String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJarChanged);
        URL url = new URL(pathOfURL);
        return url;
    }

    /**
     * getURLInJar 从Jar中获得资源
     *
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceLocation  eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public final static URL getResourceInJar(final String jarPath, final String resourceLocation) throws IOException {
        URL url = listResourcesInJar(jarPath).stream().filter(c -> c.getPath().endsWith(resourceLocation)).findFirst().get();
        return url;
    }

    /**
     * getResourceInJarAsStream 从Jar中获得资源流
     *
     * @param jarPath           eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @param resourceLocation  eg. "/com/art/Book.class"
     * @return "jar:file:/D:/Project/art/art-1.0-SNAPSHOT.jar!/com/art/Book.class"
     * @throws MalformedURLException
     */
    public final static InputStream getResourceInJarAsStream(final String jarPath, final String resourceLocation) throws IOException {
        return spellResourceInJar(jarPath, resourceLocation).openStream();
    }

    /**
     * getResourcesInJar 从Jar中获得所有资源集合
     *
     * @param jarPath eg. "D:/Project/art/art-1.0-SNAPSHOT.jar";
     * @return
     * @throws IOException
     */
    public final static List<URL> listResourcesInJar(final String jarPath) throws IOException {
        URL jarURL = new File(jarPath).toURI().toURL();
        List<JarEntry> jarEntryList = new JarFile(jarPath).stream().collect(Collectors.toList());
        List<URL> urlList = jarEntryList.stream().map(c -> {
            String resourceInJar = "/" + c.getName();
            try {
                String pathOfURL = MessageFormat.format("{0}:{1}!{2}", ProtocolType.jar, jarURL.toExternalForm(), resourceInJar);
                return new URL(pathOfURL);
            } catch (MalformedURLException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return urlList;
    }

    /**
     * path2FullName
     *
     * @param path className/packageName eg. com/company/biz | com/company/biz/Book
     * @return com.company.biz | com.company.biz.Book
     */
    public final static String path2CompleteClassName(String path) {
        path = StringSugar.removeEnd(path, ".class");
        String fullName = path.replace("/", ".");
        fullName = StringSugar.removeStart(fullName, ".");
        return fullName;
    }

    final static String[] delimiters = new String[]{"//", "/"};
    final static String delimiter = "/";

    /**
     * concat 连接多个资源路径
     *
     * @param parts
     * @return
     */
    public final static String concat(String... parts) {
        Stream<String> fixPartStream = Arrays.stream(parts).map(c -> StringSugar.replaceEach(c, delimiters, new String[]{delimiter, delimiter}));
        List<String> fixPartList = fixPartStream.collect(Collectors.toList());
        String[] fixParts = fixPartList.stream().map(c -> {
            String _a = StringSugar.removeStart(c, delimiter);
            String _b = StringSugar.removeEnd(_a, delimiter);
            return _b;
        }).toArray(String[]::new);
        String combined = String.join(delimiter, fixParts);
        String start = fixPartList.get(0).startsWith(delimiter) ? delimiter : Constant.EMPTY;
        String end = fixPartList.get(parts.length - 1).endsWith(delimiter) ? delimiter : Constant.EMPTY;
        return MessageFormat.format("{0}{1}{2}", start, combined, end);
    }

//    public final static void ff() throws FileNotFoundException {
//        org.springframework.util.ResourceUtils.getFile(
//                org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX + "static/excel/userTemplate.xlsx");
//    }

    /**
     * toCompleteClassName
     * @param resource jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
     * @return
     */
    public final static String toCompleteClassName(String resource) {
        // jar:file:/D:/all-in-one/park/target/ppppark.jar!/BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        // ->
        // /BOOT-INF/classes/com/aio/portable/park/config/BeanConfig.class
        String resourceRelative = resource.split("!")[1];
        // -> com/aio/portable/park/config/BeanConfig.class
        String classFilePath = resourceRelative.contains("classes/") ? resourceRelative.split("classes/")[1] : resourceRelative;
        // -> com.aio.portable.park.config.BeanConfig
        String completeClassName = StringSugar.removeEnd(classFilePath, ".class").replace("/", ".");
        completeClassName = StringSugar.removeStart(completeClassName, ".");
        return completeClassName;
    }

    /**
     * getResource
     */
    public final static class ByClass {
        public final static URL getResource(Class clazz, String resourceRelationPath) {
            return clazz.getResource(resourceRelationPath);
        }

        /**
         * getCodeSourceLocation
         * 如果直接执行.class文件那么会得到当前class的绝对路径。
         * 如果封装在jar包里面执行jar包那么会得到当前jar包的绝对路径。
         * @param clazz
         * @return
         */
        public final static URL getCodeSourceLocation(Class clazz) {
            URL location = clazz.getProtectionDomain().getCodeSource().getLocation();
            return location;
        }

    }

    public final static class ByClassLoader {
        /**
         * getResources 利用ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
         *
         * @param classLoader          eg. Thread.currentThread().getContextClassLoader()
         * @param resourceLocation     eg. "com/art/Book.class"
         * @return eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/sandbox/console/Book.class"
         * @throws IOException
         */
        public final static List<URL> getResources(ClassLoader classLoader, String resourceLocation) throws IOException {
            Enumeration<URL> urlEnumeration = classLoader.getResources(resourceLocation);
            List<URL> urlList = Collections.list(urlEnumeration);
            return urlList;
        }

        /**
         * getResources 利用当前ClassLoader通过资源相对位置获取其URL完整位置，包括“指定classes目录及指定jar包（如lib中）”中的“.class文件、jar文件、文件夹”
         *
         * @param resourceLocation eg. "com/art/Book.class"
         * @return                 eg. "file:/D:/Project/swiss/target/classes/com/aio/portable/swiss/sandbox/Wood.class | jar:file:/D:/Project/swiss/target/lib/console-1.0-SNAPSHOT.jar!/com/aio/portable/swiss/sandbox/Wood.class"
         * @throws IOException
         */
        public final static List<URL> getResources(String resourceLocation) throws IOException {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            return getResources(classLoader, resourceLocation);
        }

        /**
         * existResource
         * @param resourceLocation eg. "com/art/Book.class"
         * @return
         * @throws IOException
         */
        public final static boolean existResource(String resourceLocation) {
            try {
                List<URL> urlList = ByClassLoader.getResources(resourceLocation);
                boolean exist = urlList != null && urlList.size() > 0;
                return exist;
            } catch (IOException e) {
//                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        /**
         * getResourcesByClass
         *
         * @param clazz eg. Book
         * @return
         * @throws IOException
         */
        public final static List<URL> getResourcesByClass(final Class clazz) throws IOException {
            return getResourcesByClass(clazz.getTypeName());
        }

        /**
         * getResourcesByClass
         *
         * @param className eg. com.art.Book
         * @return
         * @throws IOException
         */
        public final static List<URL> getResourcesByClass(final String className) throws IOException {
            final String resourceRelationPath = ClassSugar.convertCompleteName2ResourcePath(className);
            return getResources(resourceRelationPath);
        }
    }


}