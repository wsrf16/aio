package com.aio.portable.park;


import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;


@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class,
//        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
})
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
//@EnableNetworkProxy
public class ParkApplication {
    static LogHub log;

    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext
        SpringApplication springApplication = new SpringApplication(ParkApplication.class);
//        springApplication.addListeners(new CustomPropertySourceApplicationListener());
        ConfigurableApplicationContext context = springApplication.run(args);


//        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        log = AppLogHubFactory.staticBuild();
        log.i("it is up to u. ");
        String[] beanNames = beanFactory.getBeanDefinitionNames();

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();

    }



}

