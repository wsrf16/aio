package com.aio.portable.swiss.resource;

import com.aio.portable.swiss.sandbox.a中文.Flag;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.ProtocolType;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

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

public abstract class PackageWorld {
    /**
     * getImplementationVersion
     * @param clazz
     * @return
     */
    public static String getImplementationVersion(Class clazz){
        String ver = clazz.getPackage().getImplementationVersion();
        return ver;
    }

//    public static String getImplementationVersion(){
//        String ver = Package.getPackage("java.lang").getImplementationVersion();
//        return ver;
//    }


    /**
     * getClassName
     * @param packageName  eg. com.aio.portable.swiss.sandbox
     * @return com.aio.portable.swiss.sandbox.CountDownLatchCase/com.aio.portable.swiss.sandbox.Food/com.aio.portable.swiss.sandbox.Sample$SingletonProviderBlah
     * @throws IOException
     */
    public static List<String> getQualifiedClassName(String packageName) throws IOException {
        String packagePath = packageName.replace(".", "/");
        List<URL> urlList = ResourceWorld.ByClassLoader.getResources(packagePath);
        List<String> classList = urlList.stream().flatMap(url -> {
            List<String> _classList = new ArrayList<>();
            try {
                String path = URLDecoder.decode(url.getPath(), "utf-8");
                if (ResourceUtils.isFileURL(url)) {
//                if (url.getProtocol().equals(ProtocolType.file)) {
                    // "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/com.aio.solomid"
                    _classList = getQualifiedClassNameByPath(path);
                } else if (ResourceUtils.isJarURL(url)) {
//                } else if (url.getProtocol().equals(ProtocolType.jar)) {
                    // "jar:file:/E:/Users/PPC/IdeaProjects/swiss/target/main.java.com.aio.solomid-0.0.1-SNAPSHOT.jar!/com/aio/com.aio.solomid"
                    _classList = getQualifiedClassNameByJar(path);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return _classList.stream();
        }).collect(Collectors.toList());
        return classList;
    }

    private final static String DOT = ".";
    private final static String EXT_CLASS = "class";
    private final static String EXT_DOT_CLASS = DOT + "class";
    private final static String EXT_JAR = "jar";
    private final static String EXT_DOT_JAR = DOT + "jar";
    private final static String DIR_CLASSES = "classes";


    /**
     * getClassNameByPath
     * @param packagePath : "/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/portable/swiss/ciphering"
     * @return List<String>
     */
    public static List<String> getQualifiedClassNameByPath(String packagePath) {
        List<String> classList = new ArrayList<>();
        File file = new File(packagePath);
        File[] childFiles = file.listFiles();
        if (childFiles == null)
            return classList;
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                classList.addAll(getQualifiedClassNameByPath(childFile.getPath()));
            } else {
                String childFilePath = childFile.getPath();
//                String extension = StringUtils.getFilenameExtension(childFilePath);
                if (childFilePath.endsWith(EXT_DOT_CLASS)) {
                    String _srcDirectory = MessageFormat.format("{0}" + DIR_CLASSES + "{0}", Constant.FILE_SEPARATOR);
                    childFilePath = childFilePath.substring(childFilePath.indexOf(_srcDirectory) + _srcDirectory.length(), childFilePath.lastIndexOf(EXT_DOT_CLASS));
                    childFilePath = childFilePath.replace(Constant.FILE_SEPARATOR, ".");
                    classList.add(childFilePath);
                } else if (childFilePath.endsWith(EXT_DOT_JAR)) {
                    try {
                        List<URL> resourcesInJar = ResourceWorld.getResourcesInJar(childFilePath);
                        List<String> classNameList = resourcesInJar.stream()
                                .filter(c -> c.toString().endsWith(EXT_DOT_CLASS))
                                .map(c -> ResourceWorld.convert2QualifiedClassName(c.toString()))
                                .collect(Collectors.toList());

                        classList.addAll(classNameList);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
    public static List<String> getQualifiedClassNameByJar(String packageURLInJar) throws IOException {
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
    }

    /**
     * scan
     * @param basePackages
     * @param annotations
     * @return
     */
    public final static List<Class> scan(String[] basePackages, Class<? extends Annotation>... annotations) {
        return ClassScaner.scan(basePackages, annotations);
    }

    /**
     * scan
     * @param basePackages com.aio.portable.parkdb.dao.master.model
     * @param annotations
     * @return
     */
    public final static List<Class> scan(String basePackages, Class<? extends Annotation>... annotations) {
        return ClassScaner.scan(basePackages, annotations);
    }




    public static class BlahUnit {
        public void todo() throws IOException, ClassNotFoundException {
            List<String> list = PackageWorld.getQualifiedClassName(this.getClass().getPackage().getName());
            for (String name : list) {
//            Class<?> clazz = Class.forName(name);
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(name);
                if (clazz.isAnnotationPresent(Flag.class)) {
                    Flag flag = clazz.getAnnotation(Flag.class);
                    int age = flag.age();
                    System.out.println(name);
                }
            }
        }
    }
}
