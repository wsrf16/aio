package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.controller.DynamicController;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired
    MyDatabaseTest myDatabaseTest;

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
    JWTTemplate jwtTemplate;


    @Value("${config.abc}")
    String config_abc;

//    @Autowired
//    Swagger3Properties swagger3Properties;

    @Autowired
    DynamicController dynamicController;

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) throws Exception {
        ResponseEntity objectResponseEntity = RestTemplater.get(skipSSLRestTemplate, "https://tianti.tg.unicom.local/a/login/", RestTemplater.Headers.newContentTypeApplicationJson(), String.class);

//        ExecutorService executorService = Executors.newScheduledThreadPool()
        // UriComponentsBuilder.fromHttpUrl()


//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTarget(new UserInfoEntity());
//        proxyFactory.addAdvice(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                return null;
//            }
//        });

        try {
            myDatabaseTest.blah();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        dynamicController.register();
    }


    //    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }



}



