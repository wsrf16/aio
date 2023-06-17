package com.aio.portable.park;


import com.aio.portable.swiss.suite.algorithm.identity.ULID;
import com.aio.portable.swiss.suite.bean.BeanSugar;
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
import org.springframework.core.ResolvableType;

import java.util.HashMap;
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
//        SpringContextHolder.class
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
// VMoptions: -Xlog:gc*:file=/log.txt -Xloggc:/log2.txt -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/gc.txt
public class ParkApplication {
//    static LogHub log = AppLogHubFactory.staticBuild();

    public static void main(String[] args) {
//        System.setProperty("proxySet", "true");
        ResolvableType resolvableType2 = ResolvableType.forClass(Map.class, HashMap.class);
//        "𝄞".length()
//        log.i("static", "loghub");
//        BeanSugar.Methods.getDeclaredMethodIncludeParents()
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
        System.out.println("𝄞");

//        ConfigurableApplicationContext context = SpringContextHolder.run(ParkApplication.class, c -> c = c, args);
    }


}


