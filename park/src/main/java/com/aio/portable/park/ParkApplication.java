package com.aio.portable.park;

import com.aio.portable.park.bean.friend.BestFriend;
import com.aio.portable.park.bean.friend.Person;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.NetworkProxy;
import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
        MybatisAutoConfiguration.class,
        MybatisPlusAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
//        LoggingApplicationListener.class
//        RedissonAutoConfiguration.class,
//        RedisAutoConfiguration.class,
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
// VMoptions: -Xlog:gc*:file=/log.txt -Xloggc:/log2.txt -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/gc.txt
@NetworkProxy
public class ParkApplication {
//    static LogHub log = AppLogHubFactory.staticBuild();

    public static void main(String[] args) throws URISyntaxException, InterruptedException, MalformedURLException {
        // \u000d System.out.println("coder Hydra");
        NetworkProxySugar.SystemProxies.setUseSystemProxies(true);
        System.out.println("𝄞" + "𝄞".length());
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);

//        LogHubFactory.setSingleton(new LogHubFactory() {});
        boolean initialized = LogHubProperties.isInitialized();
        boolean initial = LogHubFactory.isInitialized();
        LogHubFactory singleton = LogHubFactory.getSingleton();
    }


}


