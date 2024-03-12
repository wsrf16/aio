package com.aio.portable.park.timer;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.endpoint.http.DynamicController;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.sugar.concurrent.ThreadSugar;
import com.aio.portable.swiss.suite.io.NIOSugar;
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
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.SpringVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.List;

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
//        // \u000d System.out.println("coder Hydra");

//        while (true) {
//            ThreadSugar.sleepSeconds(10);
//            log.e("1111111111111", "mmmmmmm", new RuntimeException("eeeeeee"));
//        }


////        String s = "https://clc.test.tg.unicom.local/lcdp/user/search";
//        String s = "https://clc.test.tg.unicom.local/lcdp/manager/user/info";
////        String s = "https://meta.appinn.net/";
//
////        HttpComponentsClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLHttpComponentsClientHttpRequestFactory();
////        RestTemplate rest = new RestTemplate(factory);
////        rest.setRequestFactory(factory);
//
//        ResponseEntity<String> responseEntity = RestTemplater.client(skipSSLRestTemplate)
//                .addHeaderContentTypeApplicationJson()
//                .addHeader("Cookie", "lcdpAccessToken=882a4e58ec914ea682ef6bf3a3f74d3b")
//                .url(s)
////                .body(null)
////                .postFor(Object.class)
//                .getFor(String.class)
//                ;
//        System.out.println();
//
//
//        log.info("aaaaaaaaaaaaaaaaaaaaaa");
//
////        SpringContextHolder.restart();
//        System.out.println(config.getText());
//
//
//        slf4jLogger.info(UserInfoEntity.sample().toString());
//        commonLogging.info(UserInfoEntity.sample().toString());
////        log.info(UserInfoEntity.sample());
//
//
        if (myDatabaseTest != null)
            myDatabaseTest.blah();
//
//        dynamicController.registerTest();
//        System.out.println();
    }


    //    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
    }


}





