package com.aio.portable.park;

//import com.aio.portable.park.beanprocessor.CustomImportBeanDefinitionRegistrar;
//import com.aio.portable.park.ToMapTest;
//import com.aio.portable.park.other.jvm.MetaspaceTest;
//import com.aio.portable.park.task.ThreadLocalTest;

import com.aio.portable.swiss.suite.log.annotation.InitialLogProperties;
import com.aio.portable.swiss.suite.log.impl.PropertyBean;
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
}, scanBasePackages = "com.aio.portable")
//@ComponentScan(lazyInit = true)
// VMoptions: -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
@InitialLogProperties(initialBeanNames = PropertyBean.RABBITMQ_LOG_PROPERTIES)
public class ParkApplication {
    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ParkApplication.class, args);
        Environment environment = configurableApplicationContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();
        String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();

        ConfigurableListableBeanFactory beanFactory = configurableApplicationContext.getBeanFactory();
        Object bean = beanFactory.getBean(beanNames[0]);
    }

}
