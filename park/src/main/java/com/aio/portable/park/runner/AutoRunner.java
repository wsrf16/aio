package com.aio.portable.park.runner;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.park.config.RootConfig;
import com.aio.portable.park.test.LogTest;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.sandbox.Wood;
import com.aio.portable.swiss.sandbox.a中文.AA;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.suite.resource.ResourceSugar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.util.List;

@Configuration
public class AutoRunner implements ApplicationRunner {

//    @Autowired
//    MyDatabaseTest mybatisTest;
    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.logHub();//.setSamplerRate(1f);

    @Autowired
    RootConfig rootConfig;

    @Autowired
    LogTest logTest;



    @Override
    public void run(ApplicationArguments applicationArguments) {
//        logTest.logStyle();
//        myDatabaseTest.blah();

        try {
            String ss = ClassSugar.getPath(AA.class);
            Thread.sleep(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
