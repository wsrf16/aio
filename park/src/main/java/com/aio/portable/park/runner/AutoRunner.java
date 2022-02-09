package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.controller.DynamicController;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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

//        myDatabaseTest.blah();
        dynamicController.register();
    }


    //    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }



}



