package com.aio.portable.park.timer;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.endpoint.http.DatabaseController;
import com.aio.portable.park.endpoint.http.DynamicController;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    ApplicationConfig config;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

//    @Autowired
//    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    HttpsProxyBean httpsProxyObject;

    @Autowired
    JWTTemplate jwtTemplate;

    @Autowired
    DynamicController dynamicController;

    @Autowired
    UserInfoEntity userInfoEntity;

    @Autowired
    DatabaseController databaseController;

    LogHub log = AppLogHubFactory.staticBuild().setAsync(false);

//    LogHub ulog = UnicomAppLogHubFactory.staticBuild().setAsync(false);

    Logger logger = LoggerFactory.getLogger(AutoRunner.class);

    private static final Log commonLogging = LogFactory.getLog(AutoRunner.class);

    private static final Logger slf4jLogger = LoggerFactory.getLogger(AutoRunner.class);

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        LogHub log1 = AppLogHubFactory.staticBuild().setAsync(false);
        log.setAsync(false).info("a-{}-b-{}", new Object[]{1, 2, 3});


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ResponseEntity<String> post = RestTemplater.post(skipSSLRestTemplate, String.class, "http://10.172.147.60:30592/api/v1/metric/unit/saveCbExcelToRedis?yearMonth=2026-06&projectId=1", RestTemplater.Headers.newContentTypeApplicationJson(), null, null, null);
                    String body = post.getBody();
                    System.out.println(DateTimeSugar.UnixTime.nowUnix());
                }
            }).start();
        }


//        log.info("ss{}ss", new Object[]{1, 2, 3});
//        logger.info("bb{}bb", 1111111111);
//        logger.info("abc", "AB1234567890");
//        RabbitListenerErrorHandler rabbitListenerErrorHandler = (a, b, c) -> {
//            return c;
//        };

    }


//    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
    }


}





