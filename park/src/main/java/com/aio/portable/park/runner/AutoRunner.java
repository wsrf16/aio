package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.net.tcp.proxy.classic.HttpsProxyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    MyDatabaseTest myDatabaseTest;

    LogHub log = AppLogHubFactory.staticBuild();

    @Autowired
    public ApplicationConfig rootConfig;

//    @Autowired
//    RestTemplate restTemplate;

    @Value("${swagger.api-info.title:}")
    String swaggerApiInfoTitle;

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

    @Autowired
    HttpsProxyBean httpsProxyObject;

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
        //        UriComponentsBuilder.fromHttpUrl()
        myDatabaseTest.blah();

    }


//    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }





}



