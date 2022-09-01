package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.controller.DynamicController;
import com.aio.portable.park.service.master.UserMasterBatchBaseService;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.parkdb.dao.master.mapper.UserMasterBaseMapper;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplateType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    MyDatabaseTest myDatabaseTest;
    @Autowired
    UserMasterBatchBaseService userMasterBatchBaseService;
    @Autowired
    UserMasterBaseMapper userMasterBaseMapper;

    LogHub log = AppLogHubFactory.staticBuild();

    @Autowired
    public ApplicationConfig rootConfig;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

//    @Autowired
//    RedisTemplate redisTemplate;

//    @Autowired
//    HttpsProxyBean httpsProxyObject;

    @Autowired
    JWTTemplateType jwtTemplate;


    @Value("${config.abc}")
    String config_abc;

//    @Autowired
//    Swagger3Properties swagger3Properties;

    @Autowired
    DynamicController dynamicController;

    @Autowired
    UserInfoEntity userInfoEntity;


    private static final Log commonLogging = LogFactory.getLog(AutoRunner.class);

    @Bean
    public RetryTemplate retryTemplate() {

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);//配置的重试次数
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) throws Exception {
//        SpringContextHolder.restart();


        commonLogging.info(UserInfoEntity.sample());

        {
            // https://www.yxgapp.com/
            // https://www.facebook.com/
            String url = "http://www.yxgapp.com00/";
            ResponseEntity objectResponseEntity = RestTemplater.get(skipSSLRestTemplate, url, RestTemplater.Headers.newContentTypeApplicationJson(), String.class);
            myDatabaseTest.blah();
        }
        myDatabaseTest.blah();

        System.out.println();
//        dynamicController.register();
    }


//    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }



}



