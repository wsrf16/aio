package com.aio.portable.park.runner;

//import com.aio.portable.park.common.log.InjectedBaseLogger;

import com.aio.portable.park.test.MybatisTest;
import com.aio.portable.swiss.sugar.resource.PackageSugar;
import com.aio.portable.swiss.sugar.resource.ResourceSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeanRunner implements ApplicationRunner {

    @Autowired
    MybatisTest mybatisTest;

    @Override
    public void run(ApplicationArguments applicationArguments) {

//        mybatisTest.blah();

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

        if (1 == 1)
            return;


    }
}
