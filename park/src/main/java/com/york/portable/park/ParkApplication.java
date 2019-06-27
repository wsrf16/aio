package com.york.portable.park;

import com.york.portable.swiss.assist.log.classic.properties.LogKafkaProperties;
import com.york.portable.swiss.assist.log.classic.properties.PropertyBean;
import com.york.portable.swiss.sugar.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.util.Arrays;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class,
}, scanBasePackages = "com.york.portable")
//@DependsOn(PropertyBean.KAFKA_PROPERTIES)
//@EnableConfigurationProperties(LogKafkaProperties.class)
public class ParkApplication {
    public static void main(String[] args) {
//        AnnotationConfigEmbeddedWebApplicationContext
//        org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
        ApplicationContext configurableApplicationContext = SpringApplication.run(ParkApplication.class, args);
        String[] activeProfiles = configurableApplicationContext.getEnvironment().getActiveProfiles();
        String activeProfilesText = StringUtils.join(activeProfiles, ", ");
        String[] defaultProfiles = configurableApplicationContext.getEnvironment().getDefaultProfiles();
        String defaultProfilesText = StringUtils.join(defaultProfiles, ", ");
        String[] beanNames = configurableApplicationContext.getBeanDefinitionNames();
        //List<URL> sss = ResourceUtils.getResourcesInJar("E:/NutDisk/Program/Resource/Library/Java/_solution/Project/aio/park/swiss-1.1.0.jar");
        beanNames = beanNames;

        String name = SpringUtils.getBeanName(LogKafkaProperties.class.getSimpleName());
        Arrays.stream(activeProfiles).anyMatch(c -> Arrays.asList("development", "test", "default").contains(c));
    }

//    @Bean(initMethod = "destroy", destroyMethod = "init")
//    public AutowiredOrderTest ff() {
//        return new AutowiredOrderTest(null);
//    }

//    @Autowired
//    public AutowiredOrderTest autowiredOrderTest;


}
