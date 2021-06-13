package com.aio.portable.park;


import com.aio.portable.park.postprocessor.MutatePropertySourceApplicationListener;
import com.aio.portable.swiss.sugar.ShellSugar;
import com.aio.portable.swiss.sugar.SpringContextHolder;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.io.PathSugar;
import com.aio.portable.swiss.suite.properties.PropertySourceResolver;
import com.aio.portable.swiss.suite.systeminfo.HostInfo;
import com.aio.portable.swiss.suite.systeminfo.OSInfo;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;


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
