package com.aio.portable.park;


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
//@Prepare({PropertyBean.RABBITMQ_LOG_PROPERTIES, PropertyBean.KAFKA_LOG_PROPERTIES})
public class ParkApplication {
    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext
        ConfigurableApplicationContext context = SpringApplication.run(ParkApplication.class, args);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        String[] beanNames = beanFactory.getBeanDefinitionNames();

        Environment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();
    }

}
