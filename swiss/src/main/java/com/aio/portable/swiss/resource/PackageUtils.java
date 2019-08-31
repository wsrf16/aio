package com.aio.portable.swiss.resource;

import com.aio.portable.swiss.sandbox.a中文.Flag;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.global.ProtocolType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class PackageUtils {
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
    public static List<String> getClassName(String packageName) throws IOException {
        String packagePath = packageName.replace(".", "/");
        List<URL> urlList = ResourceUtils.ByClassLoader.getResources(packagePath);
        List<String> classList = urlList.stream().flatMap(url -> {
            List<String> _classList = new ArrayList<>();
            try {
                String path = URLDecoder.decode(url.getPath(), "utf-8");
                if (url.getProtocol().equals(ProtocolType.file)) {
                    // "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/com.aio.solomid"
                    _classList = getClassNameByPath(path);
                } else if (url.getProtocol().equals(ProtocolType.jar)) {
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

    /**
     * getClassNameByPath
     * @param packagePath : "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/aio/com.aio.solomid"
     * @return List<String>
     */
    private static List<String> getClassNameByPath(String packagePath) {
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
                String _srcDirectory = MessageFormat.format("{0}classes{0}", Constant.FILE_SEPARATOR);
                String _suffix = ".class";
                childFilePath = childFilePath.substring(childFilePath.indexOf(_srcDirectory) + _srcDirectory.length(), childFilePath.lastIndexOf(_suffix));
                childFilePath = childFilePath.replace(Constant.FILE_SEPARATOR, ".");
                classList.add(childFilePath);
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
    private static List<String> getClassNameByJar(String packageURLInJar) throws IOException {
        String[] jarInfo = packageURLInJar.split("!");
        String jarPath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
//        jarFilePath = UrlDecode.getURLDecode(jarFilePath);
        String packagePath = jarInfo[1].substring(1);

        JarFile jarFile = new JarFile(jarPath);
        Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
        List<JarEntry> jarEntryList = Collections.list(jarEntryEnumeration);
        List<String> classList = jarEntryList.stream().filter(c -> {
            String entryName = c.getName();
            return entryName.endsWith(".class") && entryName.startsWith(packagePath);
        }).map(c -> {
            String entryName = c.getName();
            String className = entryName.substring(0, entryName.lastIndexOf(".class")).replace("/", ".");
            return className;
        }).collect(Collectors.toList());

        return classList;
    }





    public static class BlahUnit {
        public void todo() throws IOException, ClassNotFoundException {
            List<String> list = PackageUtils.getClassName(this.getClass().getPackage().getName());
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
