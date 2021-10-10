package com.aio.portable.park.runner;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.annotation.LogMarker;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTCache;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    @LogMarker
    public void run(ApplicationArguments applicationArguments) {
        String abc = rootConfig.getAbc();
        String config_abc = this.config_abc;
        System.out.println("abc     " + abc);
        System.out.println("config_abc     " + config_abc);
//        BeanFactory beanFactory = SpringContextHolder.getBeanFactory();
        Environment environment = SpringContextHolder.getEnvironment();

        // UriComponentsBuilder.fromHttpUrl()
//        throw new BizException(111, "aaaaaaaaaaa");
//        RabbitMQLogProperties propertiesBean = SpringContextHolder.getPropertiesBean(RabbitMQLogProperties.class);

//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTarget(new UserInfoEntity());
//        proxyFactory.addAdvice(new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                return null;
//            }
//        });
        if (1 == 1)
            return;
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(111);
        userInfoEntity.setName("name");
        userInfoEntity.setNextId(222);




        log.i("aaaaaaa");
//
//        ArrayList<Object> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("entity", userInfoEntity);
//        map.put("list", list);
//
//        JWTCache jwtCache = new JWTCache(jwtTemplate);
//        String token = jwtCache.sign("my_name_is_abc", 1, map);
//        Map<String, Object> parse = jwtCache.parse(token);
//
//        String kkkkkkkkk = AESSugar.encrypt("abcdefghijklmnopqrstuvwxyz0987654321", "kkkkkkkkkkkkkkkka");
//        String kkkkkkkkks = AESSugar.decrypt(kkkkkkkkk, "kkkkkkkkkkkkkkkka");
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



