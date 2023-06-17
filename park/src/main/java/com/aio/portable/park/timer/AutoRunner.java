package com.aio.portable.park.timer;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.endpoint.http.DynamicController;
import com.aio.portable.park.service.master.UserMasterServiceImpl;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.park.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired(required = false)
    MyDatabaseTest myDatabaseTest;

//    @Autowired
//    ApplicationConfig rootConfig;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

//    @Autowired
//    RedisTemplate redisTemplate;

//    @Autowired
//    HttpsProxyBean httpsProxyObject;

    @Autowired
    JWTTemplate jwtTemplate;

//    @Autowired
//    Swagger3Properties swagger3Properties;

    @Autowired
    DynamicController dynamicController;

    @Autowired
    UserInfoEntity userInfoEntity;

    LogHub log = AppLogHubFactory.staticBuild().setAsync(false);

    private static final Log commonLogging = LogFactory.getLog(AutoRunner.class);

    private static final Logger slf4jLogger = LoggerFactory.getLogger(AutoRunner.class);

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) throws Exception {
        // \u000d System.out.println("coder Hydra");

//        SpringContextHolder.restart();

        slf4jLogger.info(UserInfoEntity.sample().toString());
        commonLogging.info(UserInfoEntity.sample().toString());
//        log.info(UserInfoEntity.sample());


        if (myDatabaseTest != null)
            myDatabaseTest.blah();

        dynamicController.registerTest();
        System.out.println();
    }


//    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }



}



