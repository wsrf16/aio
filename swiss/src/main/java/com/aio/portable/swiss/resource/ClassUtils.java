package com.aio.portable.swiss.resource;

import com.aio.portable.swiss.bean.serializer.json.GsonUtil;
import com.aio.portable.swiss.bean.serializer.json.JacksonUtil;
import com.aio.portable.swiss.sandbox.Wood;
import com.aio.portable.swiss.sandbox.a中文.AA;

import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class ClassUtils {
    /**
     * 获取类所有的路径
     *
     * @param clazz
     * @return
     */
    public static String getPath(final Class clazz) throws MalformedURLException {
        final String clazzAsResource = convertClassName2ResourcePath(clazz.getTypeName());
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
                                .concat("!/").concat(clazzAsResource));
                    else if (new File(location.getFile()).isDirectory())
                        location = new URL(location, clazzAsResource);
                }
            }
        }
        if (location == null) {
            final ClassLoader clsLoader = clazz.getClassLoader();
            location = clsLoader != null ?
                    clsLoader.getResource(clazzAsResource) :
                    ClassLoader.getSystemResource(clazzAsResource);
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
        String resource = convertClassName2ResourcePath(className);
        return Resources.ByClassLoader.existResource(resource);
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

    /**
     * newInstance
     * @param clazz
     * @param <T>
     * @return
     */
    public synchronized final static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }




    private static class BlahUnit {
        private static void todo() throws IOException {
            String ss = ClassUtils.getPath(AA.class);
            boolean b1 = ClassUtils.exist("Wood");
            boolean b2 = ClassUtils.exist("Wood");


            if (existJackson())
                System.out.println(JacksonUtil.obj2Json(new Wood() {
                    {
                        setA(888);
                    }
                }));

            System.out.println();

            if (existGson())
                System.out.println(GsonUtil.obj2Json(new Wood() {
                    {
                        setA(888);
                    }
                }));
        }

        private static boolean existJackson() throws IOException {
            return ClassUtils.exist(("com.fasterxml.jackson.databind.JsonSerializer"));
        }

        private static boolean existGson() throws IOException {
            return ClassUtils.exist(("com.google.gson.Gson"));
        }
    }
}
