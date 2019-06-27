package com.york.portable.park.other;

import com.york.portable.park.ParkApplication;
import com.york.portable.swiss.assist.log.classic.properties.LogKafkaProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
            String path = ParkApplication.class.getResource("/1.properties").getFile();
            System.out.println(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        // java.lang.NullPointerException
        try {
            System.out.println("--ParkApplication.class.getResource(\"1.properties\").getFile()");
            String path = ParkApplication.class.getResource("1.properties").getFile();
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
}
