package com.aio.portable.park.timer;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.endpoint.http.DynamicController;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecord;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired(required = false)
    MyDatabaseTest myDatabaseTest;

    @Autowired
    ApplicationConfig config;

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
//    @LogRecord
    public void run(ApplicationArguments applicationArguments) throws Exception {
//        ResponseEntity<String> stringResponseEntity = RestTemplater.get(skipSSLRestTemplate, String.class, "http://198.18.0.24", RestTemplater.Headers.newContentTypeApplicationJson(), null);
        // \u000d System.out.println("coder Hydra");

//        SpringContextHolder.restart();
        System.out.println(config.getText());
//        int i = 1;
//        if (i == 1)
//            return;


        ResponseEntity<Map> kkkkk = RestTemplater.client(restTemplate)
                .url("http://localhost:7777/abc")
                .addParam("k", 8)
                .body("{k:0}")
                .putFor(new ParameterizedTypeReference<HashMap>() {
                });

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





