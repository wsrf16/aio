package com.aio.portable.park;

import com.aio.portable.park.dao.master.model.Book;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.NetworkProxy;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;


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

    public ResponseWrapper<Book> ff() {
        return null;
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
//        log.i("static", "loghub");
//        boolean primitiveOrWrapper = ClassUtils.isPrimitiveOrWrapper(String.class);


//        // \u000d System.out.println("coder Hydra");
        NetworkProxySugar.SystemProxies.setUseSystemProxies(true);
        System.out.println("𝄞" + "𝄞".length());
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);

//        LogHubFactory.setSingleton(new LogHubFactory() {});
        boolean initialized = LogHubProperties.initialized();
        boolean initial = LogHubFactory.isInitial();
        LogHubFactory singleton = LogHubFactory.getSingleton();

        System.out.println();
    }




}


