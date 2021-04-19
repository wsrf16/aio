package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sandbox.Wood;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.suite.resource.ResourceSugar;
import com.aio.portable.swiss.suite.resource.StreamClassLoader;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.awt.print.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class ResourceTest {
    LogHub log = LogHub.build(new Slf4JLog());

    {
        try {
            String jar = "./spring-boot-starter-swiss/target/spring-boot-starter-swiss-1.1.10-SNAPSHOT.jar";
            String resource = "com/aio/portable/swiss/sandbox/Wood.class";

            List<URL> resources0 = ResourceSugar.ByClassLoader.getResources(resource);
            List<URL> resources1 = ResourceSugar.ByClassLoader.getResourcesByClass(Wood.class.getTypeName());
            List<URL> resources2 = ResourceSugar.ByClassLoader.getResourcesByClass(Wood.class);

            List<URL> urls = ResourceSugar.listResourcesInJar(jar);
            URL resourceInJar = ResourceSugar.getResourceInJar(jar, resource);

            URL url = urls.get(156);
            Class<?> clazz1 = ClassLoaderSugar.loadedClass(url);
            String className = ResourceSugar.toQualifiedClassName(url.toString());
            Class<?> clazz2 = ClassLoaderSugar.loadedClass(urls, className);

            List<String> qualifiedClassNameList = PackageSugar.getQualifiedClassNameByPath("./park/target");
//            List<String> qualifiedClassNameByJar = PackageSugar.getQualifiedClassNameByJar(jar);

            List<String> qualifiedClassName = PackageSugar.getQualifiedClassName("com.aio.portable.swiss.global");
            Thread.sleep(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // 只能用于从非jar包中获取资源
        // jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
        try {
            System.out.println("--new ClassPathResource(\"1.properties\").getFile().toString()");
            String classPathResourceText = new ClassPathResource("1.properties").getFile().toString();
            System.out.println(classPathResourceText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    {
        // 只能用于从非jar包中获取资源
        // jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
        try {
            System.out.println("--ResourceUtils.getFile(\"classpath:1.properties\")");
            File file = ResourceUtils.getFile("classpath:1.properties");
            System.out.println(file.toURI());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    {
        // jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
        try {
            System.out.println("--ResourceUtils.getURL");
            String path = ResourceUtils.getURL("classpath:1.properties").toString();
            System.out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    {
        // file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
        try {
            System.out.println("--ParkApplication.class.getResource(\"/1.properties\").getFile()");
            String path = this.getClass().getResource("/1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // java.lang.NullPointerException
        try {
            System.out.println("--ParkApplication.class.getResource(\"1.properties\").getFile()");
            String path = this.getClass().getResource("1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
        try {
            System.out.println("--KafkaLogProperties.class.getResource(\"/1.properties\").getFile()");
            String path = KafkaLogProperties.class.getResource("/1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // java.lang.NullPointerException
        try {
            System.out.println("--KafkaLogProperties.class.getResource(\"1.properties\").getFile()");
            String path = KafkaLogProperties.class.getResource("1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }









    @Test
    public static void foobar() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ResourceSugar.ByClassLoader.getResources("com/aio/portable/swiss/sandbox/a中文/AA.class");
        ResourceSugar.ByClassLoader.getResourcesByClass("Wood");
        ResourceSugar.ByClassLoader.getResourcesByClass(Book.class);


        String jarPath = new File("console-1.0-SNAPSHOT.jar").getAbsolutePath();
        String resourceInJar = "/sandbox/console/Book.class";
        URL url = ResourceSugar.getResourceInJar(jarPath, resourceInJar);
        List<URL> urlList = ResourceSugar.listResourcesInJar(jarPath);

        {
            String className = ResourceSugar.path2QualifiedClassName(resourceInJar);
            Class clazz = StreamClassLoader.buildByFile("console-1.0-SNAPSHOT.jar").loadClassByBinary(className);
            className = "Wood";
            Class clazz1 = StreamClassLoader.buildByFile("target/classes/com/aio/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Class clazz2 = StreamClassLoader.buildByResource("com/aio/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Object obj1 = clazz.getDeclaredConstructor().newInstance();
        }
        {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + jarPath)});
            Class clazz = urlClassLoader.loadClass("sandbox.console.Book");
            Object obj = clazz.getDeclaredConstructor().newInstance();
            Object obj1 = clazz.getDeclaredConstructor().newInstance();
        }
    }

    {
        String resourceLocation = "/com/aio/portable/swiss/sandbox/Wood.class";
        String classname = "Wood";
        Class clazz = Wood.class;



        String jarPath;
        File file;
        file = new File("swiss/target/swiss-1.1.4-SNAPSHOT.jar");
        jarPath = file.exists() ? file.getAbsolutePath() : "";

        file = new File("lib/swiss-1.1.4-SNAPSHOT.jar");
        jarPath = jarPath == "" ? (file.exists() ? file.getAbsolutePath() : jarPath) : jarPath;




        try {
            System.out.println(resourceLocation);
            List<URL> r1 = ResourceSugar.ByClassLoader.getResources(resourceLocation);
            log.i("r1！！！！！", r1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(classname);
            List<URL> r2 = ResourceSugar.ByClassLoader.getResourcesByClass(classname);
            log.i("r2！！！！！", r2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<URL> r3 = ResourceSugar.ByClassLoader.getResourcesByClass(clazz);
            log.i("r3！！！！！", r3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(jarPath);
            System.out.println(resourceLocation);
            URL url = ResourceSugar.getResourceInJar(jarPath, resourceLocation);
            List<URL> r4 = ResourceSugar.listResourcesInJar(jarPath);
            log.i("r4！！！！！", url);
            log.i("r4！！！！！", r4);
        } catch (Exception e) {
            e.printStackTrace();
        }

//            String className = ResourceUtils.path2FullName(resourceInJar);
//            Class clazz1 = StreamClassLoader.buildByFile(jarPath).loadClassByBinary(className);

//            String ss = "jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/";
        try {
            System.out.println(jarPath);
            Class r5 = StreamClassLoader.buildByFile(jarPath).loadClassByBinary(classname);
            clazz = r5;
            log.i("r5！！！！！", r5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
