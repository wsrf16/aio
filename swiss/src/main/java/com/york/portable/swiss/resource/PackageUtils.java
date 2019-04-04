package com.york.portable.swiss.resource;

import com.york.portable.swiss.global.Constant;
import com.york.portable.swiss.global.ProtocolType;

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

    public static String getImplementationVersion(){
        String ver = Package.getPackage("java.lang").getImplementationVersion();
        return ver;
    }


    /**
     * getClassName
     * @param packageName  eg. com.york.portable.swiss.sandbox
     * @return com.york.portable.swiss.sandbox.CountDownLatchCase/com.york.portable.swiss.sandbox.Food/com.york.portable.swiss.sandbox.Sample$SingletonProviderBlah
     * @throws IOException
     */
    public static List<String> getClassName(String packageName) throws IOException {
        String packagePath = packageName.replace(".", "/");
        List<URL> urlList = ResourceUtils.getResourcesInClassFile(packagePath);
        List<String> classList = urlList.stream().flatMap(url -> {
            List<String> _classList = new ArrayList<>();
            try {
                String path = URLDecoder.decode(url.getPath(), "utf-8");
                if (url.getProtocol().equals(ProtocolType.file)) {
                    // "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/york/com.york.solomid"
                    _classList = getClassNameByPath(path);
                } else if (url.getProtocol().equals(ProtocolType.jar)) {
                    // "jar:file:/E:/Users/PPC/IdeaProjects/swiss/target/main.java.com.york.solomid-0.0.1-SNAPSHOT.jar!/com/york/com.york.solomid"
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
     * @param packagePath : "file:/E:/Users/PPC/IdeaProjects/swiss/target/classes/com/york/com.york.solomid"
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
     * @param packageURLInJar : "jar:file:/E:/Users/PPC/IdeaProjects/swiss/target/main.java.com.york.solomid-0.0.1-SNAPSHOT.jar!/com/york/com.york.solomid"
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
}
