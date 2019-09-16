package com.aio.portable.park.other.test;

import com.aio.portable.park.common.LogFactory;
import com.aio.portable.park.ParkApplication;
import com.aio.portable.swiss.assist.log.classic.properties.LogKafkaProperties;
import com.aio.portable.swiss.assist.log.hub.LogHub;
import com.aio.portable.swiss.resource.Resources;
import com.aio.portable.swiss.resource.StreamClassLoaders;
import com.aio.portable.swiss.sandbox.Wood;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ResourceTest {
    LogHub log = LogFactory.singletonInstance().build();

    {
        {
            // 只能用于从非jar包中获取资源
            // jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
            try {
                System.out.println("--1.new ClassPathResource(\"1.properties\").getFile().toString()");
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
                System.out.println("--2.ResourceUtils.getFile(\"classpath:1.properties\")");
                File file = ResourceUtils.getFile("classpath:1.properties");
                System.out.println(file.toURI());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        {
            // jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
            try {
                System.out.println("--√3.ResourceUtils.getURL");
                String path = ResourceUtils.getURL("classpath:1.properties").toString();
                System.out.println(path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        {
            // file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
            try {
                System.out.println("--√4.ParkApplication.class.getResource(\"/1.properties\").getFile()");
                String path = ParkApplication.class.getResource("/1.properties").getFile();
                System.out.println(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            // java.lang.NullPointerException
            try {
                System.out.println("--5.ParkApplication.class.getResource(\"1.properties\").getFile()");
                String path = ParkApplication.class.getResource("1.properties").getFile();
                System.out.println(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            // file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar!/1.properties
            try {
                System.out.println("--√6.LogKafkaProperties.class.getResource(\"/1.properties\").getFile()");
                String path = LogKafkaProperties.class.getResource("/1.properties").getFile();
                System.out.println(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        {
            // java.lang.NullPointerException
            try {
                System.out.println("--7.LogKafkaProperties.class.getResource(\"1.properties\").getFile()");
                String path = LogKafkaProperties.class.getResource("1.properties").getFile();
                System.out.println(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }











    {
        String resourceLocation = "/com/aio/portable/swiss/sandbox/Wood.class";
        String resourceLocation2 = "/com/aio/portable/swiss/sandbox/a中文/AA.class";
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
            List<URL> r1 = Resources.ByClassLoader.getResources(resourceLocation);
            log.i("r1！！！！！", r1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(classname);
            List<URL> r2 = Resources.ByClassLoader.getResourcesByClassName(classname);
            log.i("r2！！！！！", r2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            List<URL> r3 = Resources.ByClassLoader.getResourcesByClass(clazz);
            log.i("r3！！！！！", r3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.out.println(jarPath);
            System.out.println(resourceLocation);
            URL url = Resources.getResourceInJar(jarPath, resourceLocation);
            List<URL> r4 = Resources.getResourcesInJar(jarPath);
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
            Class r5 = StreamClassLoaders.buildByFile(jarPath).loadClassByBinary(classname);
            clazz = r5;
            log.i("r5！！！！！", r5);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}