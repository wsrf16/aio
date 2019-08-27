package com.york.portable.park;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
//import com.york.portable.park.beanprocessor.CustomImportBeanDefinitionRegistrar;
//import com.york.portable.park.ToMapTest;
//import com.york.portable.park.other.jvm.MetaspaceTest;
//import com.york.portable.park.task.ThreadLocalTest;
import com.york.portable.park.config.mybatis.MasterDataSourceConfiguration;
import com.york.portable.park.config.mybatis.SlaveDataSourceConfiguration;
import com.york.portable.swiss.assist.log.classic.properties.LogKafkaProperties;
import com.york.portable.swiss.assist.log.classic.properties.PropertyBean;
import com.york.portable.swiss.sugar.SpringContextUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.util.Arrays;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class
}, scanBasePackages = "com.york.portable")
//@ComponentScan(lazyInit = true)
//@DependsOn(PropertyBean.KAFKA_PROPERTIES)
//@Import({MasterDataSourceConfiguration.class, SlaveDataSourceConfiguration.class})
public class ParkApplication {
    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext
        ApplicationContext configurableApplicationContext = SpringApplication.run(ParkApplication.class, args);
        Environment environment = configurableApplicationContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] defaultProfiles = environment.getDefaultProfiles();
        String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();

//    @Bean(initMethod = "destroy", destroyMethod = "init")
//    public AutowiredOrderTest ff() {
//        return new AutowiredOrderTest(null);
//    }

//    @Autowired
//    public AutowiredOrderTest autowiredOrderTest;
    }

}
