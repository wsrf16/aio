package com.aio.portable.park;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
//import com.aio.portable.park.beanprocessor.CustomImportBeanDefinitionRegistrar;
//import com.aio.portable.park.ToMapTest;
//import com.aio.portable.park.other.jvm.MetaspaceTest;
//import com.aio.portable.park.task.ThreadLocalTest;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
//        DruidDataSourceAutoConfigure.class,
//        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
}, scanBasePackages = "com.aio.portable")
//@EntityScan("com.aio.portable")

//@ComponentScan(lazyInit = true)
//@DependsOn(PropertyBean.KAFKA_PROPERTIES)
//@Import({MasterDataSourceConfiguration.class, SlaveDataSourceConfiguration.class})
//@Import({ThirdDataSourceConfiguration.class})
// -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
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
