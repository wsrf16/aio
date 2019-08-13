package com.york.portable.swiss.resource;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.List;

public class ClassUtils {
    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return
     */
    public static String getPath(final Class clazz) throws MalformedURLException {
        final String clsAsResource = convertClassName2ResourcePath(clazz.getTypeName());
        URL location = null;
        final ProtectionDomain domain = clazz.getProtectionDomain();
        if (domain != null) {
            final CodeSource cs = domain.getCodeSource();
            if (cs != null) location = cs.getLocation();
            if (location != null) {
                if (org.springframework.util.ResourceUtils.URL_PROTOCOL_FILE.equals(location.getProtocol())) {
                    if (location.toExternalForm().endsWith(".jar") ||
                            location.toExternalForm().endsWith(".zip"))
                        location = new URL((org.springframework.util.ResourceUtils.URL_PROTOCOL_JAR + ":").concat(location.toExternalForm())
                                .concat("!/").concat(clsAsResource));
                    else if (new File(location.getFile()).isDirectory())
                        location = new URL(location, clsAsResource);
                }
            }
        }
        if (location == null) {
            final ClassLoader clsLoader = clazz.getClassLoader();
            location = clsLoader != null ?
                    clsLoader.getResource(clsAsResource) :
                    ClassLoader.getSystemResource(clsAsResource);
        }
        return location.toString();
    }






    /**
     * hasClassByCurrentThreadClassLoader 判断是否存在某一个类
     *
     * @param className eg. com.art.Book
     * @return
     * @throws IOException
     */
    public static boolean exist(String className) throws IOException {
        return hasClassByCurrentThreadClassLoader(className);
    }


    /**
     * hasClassByCurrentThreadClassLoader 判断是否存在某一个类
     *
     * @param className eg. com.art.Book
     * @return
     * @throws IOException
     */
    private static boolean hasClassByCurrentThreadClassLoader(String className) throws IOException {
        String resource = convertClassName2ResourcePath(className);
        List<URL> urlList = ResourceUtils.getResourcesInClassFile(resource);
        boolean exist = urlList != null && urlList.size() > 0;
        return exist;
    }

    /**
     * getShortName -> org.springframework.util.ClassUtils.getShortName
     * @param className
     * @return
     */
    public static String getShortName(String className) {
        String shortClassName = org.springframework.util.ClassUtils.getShortName(className);
        return shortClassName;
    }

    /**
     * getBeanName
     * @param shortClassName
     * @return
     */
    public static String getBeanName(String shortClassName) {
        return Introspector.decapitalize(shortClassName);
    }


    /**
     * fullName2Name
     *
     * @param fullName className/packageName eg. com.company.biz | com.company.biz.Book
     * @return com/company/biz | com/company/biz/Book
     */
    public static String convertClassName2ResourcePath(String fullName) {
        String path;
//        path = fullName.replace('.', '/').concat(".class");
        path = org.springframework.util.ClassUtils.convertClassNameToResourcePath(fullName).concat(".class");
        return path;
    }
}
