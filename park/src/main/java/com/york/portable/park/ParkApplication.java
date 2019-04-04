package com.york.portable.park;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;

import java.util.Arrays;

//@MapperScan(basePackages = {"com.york.portable.park.parkdb.dao.master.mapper"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        KafkaAutoConfiguration.class,
}, scanBasePackages = "com.york.portable")
@DependsOn("logKafkaProperties")
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

        Arrays.stream(activeProfiles).anyMatch(c -> Arrays.asList("dev", "test", "default").contains(c));
    }

//    @Bean(initMethod = "destroy", destroyMethod = "init")
//    public AutowiredOrderTest ff() {
//        return new AutowiredOrderTest(null);
//    }

//    @Autowired
//    public AutowiredOrderTest autowiredOrderTest;


}
