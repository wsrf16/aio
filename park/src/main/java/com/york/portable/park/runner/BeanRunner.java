package com.york.portable.park.runner;

//import com.york.portable.park.common.log.InjectedBaseLogger;

import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.park.other.test.MybatisTest;
import com.york.portable.park.other.test.ResourceTest;
import com.york.portable.park.schedule.Schedule;
import com.york.portable.swiss.assist.log.hub.LogHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BeanRunner implements ApplicationRunner {
    @Autowired
    Schedule schedule;

    @Autowired(required = false)
    MybatisTest mybatisTest;

    LogHub log = CustomLogHubFactory.singletonInstance().build();


    private void todo() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
//        String jarPath = new File("swiss/console-1.0-SNAPSHOT.jar").getAbsolutePath();


//        String jarPath = new File("lib/swiss-1.1.4-SNAPSHOT.jar").getAbsolutePath();
//        String jarPath = ("jar:file:/data1/services/park/lib/swiss/console-1.0-SNAPSHOT.jar");
//                          "jar:file:/data1/services/park/lib/swiss-1.1.4-SNAPSHOT.jar/com/york/portable/swiss/sandbox/Wood.class"


    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        new ResourceTest();

        if (1 == 1)
            return;


        schedule.process();
    }
}
