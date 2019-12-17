package com.aio.portable.park.runner;

//import com.aio.portable.park.common.log.InjectedBaseLogger;

import com.aio.portable.park.config.LogFactory;
import com.aio.portable.park.test.MybatisTest;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.sugar.RegexSugar;
import com.aio.portable.swiss.sugar.resource.PackageSugar;
import com.aio.portable.swiss.sugar.resource.ResourceSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BeanRunner implements ApplicationRunner {

    @Autowired
    MybatisTest mybatisTest;

    LogHub log = LogFactory.singletonInstance().build().setSamplerRate(1f);

    @Override
    public void run(ApplicationArguments applicationArguments) {
//        Object[] integers = {1, 1, 1};
//        log.d("a", {1,1,1});

        ApplicationHome h = new ApplicationHome(getClass());
        File jarF = h.getSource();
        System.out.println(jarF.getParentFile().toString());

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.exit(0);






//        mybatisTest.blah();

        String root = "D:/NutDisk/Program/Resource/Library/Java/_solution/Project/all-in-one/";
        root = "./";
        String file = root + "park/target/park.jar";

        List<String> list = null;
        try {
            list = new ArrayList<>();
            list.add("list");
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.debug("a", "b");
        log.info("a", list);
        log.info("a{}{}{}", new String[]{"b","c","d"});
        log.info("a", "list{}", new Object[]{"aaa"});

        String info = "list{}-{}-{}";
        Object[] arguments = new Object[]{"aaa", "bbb","ccc"};
        info = RegexSugar.replace("\\{\\}", info, arguments);
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
