package com.aio.portable.park.runner;

//import com.aio.portable.park.common.log.InjectedBaseLogger;

import com.aio.portable.park.test.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BeanRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) {


        if (1 == 1)
            return;


    }
}
