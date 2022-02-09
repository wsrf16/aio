package com.aio.portable.swiss.sugar.resource;

import com.aio.portable.swiss.global.Constant;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public abstract class PackageSugar {
    /**
     * getImplementationVersion
     * @param clazz
     * @return
     */
    public static final String getImplementationVersion(Class<?> clazz){
        String ver = clazz.getPackage().getImplementationVersion();
        return ver;
    }

//    public static final String getImplementationVersion(){
//        String ver = Package.getPackage("java.lang").getImplementationVersion();
//        return ver;
//    }


    /**
     * getClassNameList
     * @param packageName  eg. com.aio.portable.swiss.sandbox
     * @return com.aio.portable.swiss.sandbox.CountDownLatchCase/com.aio.portable.swiss.sandbox.Food/com.aio.portable.swiss.sandbox.Sample$SingletonProviderBlah
     * @throws IOException
     */
    public static final List<String> getClassNameList(String packageName) {
        String packagePath = packageName.replace(".", "/");
        List<URL> urlList = ResourceSugar.ByClassLoader.getResourceURLs(packagePath);
        List<String> classList = urlList.stream().flatMap(url -> {
            List<String> _classList = new ArrayList<>();
            try {
                String path = URLDecoder.decode(url.getPath(), "utf-8");
                if (ResourceUtils.isFileURL(url)) {
//                if (url.getProtocol().equals(ProtocolType.file)) {
                    // "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/com.aio.solomid"
                    _classList = getClassNameByPath(path);
                } else if (ResourceUtils.isJarURL(url)) {
//                } else if (url.getProtocol().equals(ProtocolType.jar)) {
                    // "jar:file:/E:/Users/PPC/IdeaProjects/swiss/target/main.java.com.aio.solomid-0.0.1-SNAPSHOT.jar!/com/aio/com.aio.solomid"
                    _classList = getClassNameByJar(path);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return _classList.stream();
        }).collect(Collectors.toList());
        return classList;
    }

//    private static final String DOT = ".";
//    private static final String EXT_CLASS = "class";
    private static final String EXT_DOT_CLASS = ".class";
    private static final String EXT_JAR = "jar";
    private static final String EXT_DOT_JAR = ".jar";
    private static final String DIR_CLASSES = "classes";


    /**
     * getClassNameByPath(包括".class"和".jar中的.class"里面的类)
     * @param packagePath : "/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/portable/swiss/ciphering"
     * @return List<String>
     */
    public static final List<String> getClassNameByPath(String packagePath) {
        List<String> classList = new ArrayList<>();
        File file = new File(packagePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null)
            return classList;
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                classList.addAll(getClassNameByPath(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
//                String extension = StringUtils.getFilenameExtension(childFilePath);
                if (childFilePath.endsWith(EXT_DOT_CLASS)) {
                    String _srcDirectory = MessageFormat.format("{0}" + DIR_CLASSES + "{0}", Constant.FILE_SEPARATOR);
                    childFilePath = childFilePath.substring(childFilePath.indexOf(_srcDirectory) + _srcDirectory.length(), childFilePath.lastIndexOf(EXT_DOT_CLASS));
                    childFilePath = childFilePath.replace(Constant.FILE_SEPARATOR, ".");
                    classList.add(childFilePath);
                } else if (childFilePath.endsWith(EXT_DOT_JAR)) {
                    List<URL> resourcesInJar = ResourceSugar.getAllJarResourceURLs(childFilePath);
                    List<String> classNameList = resourcesInJar.stream()
                            .filter(c -> c.toString().endsWith(EXT_DOT_CLASS))
                            .map(c -> ResourceSugar.convertURLToClassName(c.toString()))
                            .collect(Collectors.toList());

                    classList.addAll(classNameList);
                }
            }
        }
        return classList;
    }

    /**
     * getClassNameByJar
     * @param packageURLInJar : "jar:file:/E:/Users/PPC/IdeaProjects/swiss/target/main.java.com.aio.solomid-0.0.1-SNAPSHOT.jar!/com/aio/com.aio.solomid"
     * @return
     * @throws IOException
     */
    public static final List<String> getClassNameByJar(String packageURLInJar) {
        try {
            String[] jarInfo = packageURLInJar.split("!");
            String jarPath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
//        jarFilePath = UrlDecode.getURLDecode(jarFilePath);
            String packagePath = jarInfo[1].substring(1);

            JarFile jarFile = new JarFile(jarPath);
            Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
            List<JarEntry> jarEntryList = Collections.list(jarEntryEnumeration);
            List<String> classList = jarEntryList.stream().filter(c -> {
                String entryName = c.getName();
                return entryName.endsWith(EXT_DOT_CLASS) && entryName.startsWith(packagePath);
            }).map(c -> {
                String entryName = c.getName();
                String className = entryName.substring(0, entryName.lastIndexOf(EXT_DOT_CLASS)).replace("/", ".");
                return className;
            }).collect(Collectors.toList());

            return classList;
        } catch (IOException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * scan
     * @param basePackages
     * @param annotations
     * @return
     */
    public static final List<Class> scan(String[] basePackages, Class<? extends Annotation>... annotations) {
        return ClassScanner.scan(basePackages, annotations);
    }

    /**
     * scan
     * @param basePackages com.aio.portable.parkdb.dao.master.model
     * @param annotations
     * @return
     */
    public static final List<Class> scan(String basePackages, Class<? extends Annotation>... annotations) {
        return ClassScanner.scan(basePackages, annotations);
    }



}
