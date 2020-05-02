package com.aio.portable.park.runner;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.park.config.RootConfig;
import com.aio.portable.park.test.LogTest;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.algorithm.cipher.CipherSugar;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.suite.resource.ResourceSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class BeanRunner implements ApplicationRunner {

//    @Autowired
//    MyDatabaseTest mybatisTest;

    LogHub log = AppLogHubFactory.singletonInstance().build();//.setSamplerRate(1f);

    @Autowired
    RootConfig rootConfig;

    @Autowired
    LogTest logTest;

    @Override
    public void run(ApplicationArguments applicationArguments) {
        logTest.logStyle();
//        while(true) {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.i("a", "aaaaaaaaaaaaa");
//
//            if (1==2)
//                break;
//        }

    }

    private void aa() {
        //        ResourceSugar.ByClassLoader.getResources("classpath:config/mapper/*.xml")
        System.exit(0);
        String root = "D:/NutDisk/Program/Resource/Library/Java/_solution/Project/all-in-one/";
        root = "./";
        String file = root + "park/target/park.jar";


        try {
            List<URL> collect = ResourceSugar.getResourcesInJar(file).stream().collect(Collectors.toList());
            String url = collect.get(128).toString();

            // "jar:file:/D:/NutDisk/Program/Resource/Library/Java/_solution/Project/all-in-one/park/target/ppppark.jar!/BOOT-INF/lib/park-db-1.1.4-SNAPSHOT.jar!/com/aio/portable/parkdb/dao/master/model/Book.class";

//            ResourceWorld.convert2QualifiedClassName(url)

            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + file)});
            URLClassLoader urlClassLoader1 = urlClassLoader;
            Class<?> aClass = urlClassLoader.loadClass("com.aio.portable.park.config.BeanConfig");

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> path1 = PackageSugar.getQualifiedClassNameByPath("file:/" + file);
        List<String> path2 = PackageSugar.getQualifiedClassNameByPath(root + "park/target");


//        try {
//            PackageWorld.getQualifiedClassNameByJar("file:/" + file + "!/com/aio/portable/parkdb/dao/master/model");
//
//            List<String> qualifiedClassName = PackageWorld.getQualifiedClassName("com.aio.portable.swiss.ciphering");
//            List<String> qualifiedClassName2 = PackageWorld.getQualifiedClassName("com.aio.portable.swiss.sandbox");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
