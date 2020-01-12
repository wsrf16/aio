package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.structure.log.base.classic.properties.LogKafkaProperties;
import com.aio.portable.swiss.sugar.resource.ResourceSugar;
import com.aio.portable.swiss.sugar.resource.StreamClassLoader;
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
            System.out.println("--LogKafkaProperties.class.getResource(\"/1.properties\").getFile()");
            String path = LogKafkaProperties.class.getResource("/1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // java.lang.NullPointerException
        try {
            System.out.println("--LogKafkaProperties.class.getResource(\"1.properties\").getFile()");
            String path = LogKafkaProperties.class.getResource("1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }









    @Test
    public static void todo() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        ResourceSugar.ByClassLoader.getResources("com/aio/portable/swiss/sandbox/a中文/AA.class");
        ResourceSugar.ByClassLoader.getResourcesByClassName("Wood");
        ResourceSugar.ByClassLoader.getResourcesByClass(Book.class);


        String jarPath = new File("console-1.0-SNAPSHOT.jar").getAbsolutePath();
        String resourceInJar = "/sandbox/console/Book.class";
        URL url = ResourceSugar.getResourceInJar(jarPath, resourceInJar);
        List<URL> urlList = ResourceSugar.getResourcesInJar(jarPath);

        {
            String className = ResourceSugar.path2FullName(resourceInJar);
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
}
