package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger3Properties;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

    @Autowired
    public StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    HttpsProxyBean httpsProxyObject;

    @Autowired
    JWTTemplate jwtTemplate;


    @Value("${config.abc}")
    String config_abc;

    @Autowired
    Swagger3Properties swagger3Properties;

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
        String abc = rootConfig.getAbc();
        String config_abc = this.config_abc;
        System.out.println("abc     " + abc);
        System.out.println("config_abc     " + config_abc);
        log.info("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//        BeanFactory beanFactory = SpringContextHolder.getBeanFactory();
        Environment environment = SpringContextHolder.getEnvironment();
        ThreadGroup aaaa = new ThreadGroup("aaaa");
        Thread thread1 = new Thread(aaaa, () -> System.out.println());
        thread1.setName("1111");

        Thread thread2 = new Thread(aaaa, () -> System.out.println());
        thread2.setName("1111");
        thread1.start();
        thread2.start();

        // UriComponentsBuilder.fromHttpUrl()
        RabbitMQLogProperties propertiesBean = SpringContextHolder.getPropertyBean(RabbitMQLogProperties.class);

//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTarget(new UserInfoEntity());
//        proxyFactory.addAdvice(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                return null;
//            }
//        });




//

//
//
//        Looper looper = new Looper(() -> log.i(map), 10, TimeUnit.SECONDS);
//        try {
//            looper.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.i(e);
//        }
//        myDatabaseTest.blah();
    }


    //    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {

    }





}



