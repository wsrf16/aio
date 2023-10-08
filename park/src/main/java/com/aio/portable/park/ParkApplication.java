package com.aio.portable.park;


import com.aio.portable.park.bean.MenuEntity;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.NetworkProxy;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.aio.portable.swiss.suite.bean.node.tree.recursion.RecursiveTree;
import com.aio.portable.swiss.suite.net.tcp.proxy.NetworkProxySugar;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.fasterxml.jackson.core.type.TypeReference;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


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
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
// VMoptions: -Xlog:gc*:file=/log.txt -Xloggc:/log2.txt -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/gc.txt
@NetworkProxy
public class ParkApplication {
//    static LogHub log = AppLogHubFactory.staticBuild();

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        NetworkProxySugar.SystemProxies.setUseSystemProxies(true);
//        log.i("static", "loghub");
//        BeanSugar.Methods.getDeclaredMethodIncludeParents()
        System.out.println("𝄞" + "𝄞".length());
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
    }




}


