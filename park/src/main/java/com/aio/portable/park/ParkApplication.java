package com.aio.portable.park;

import com.aio.portable.swiss.suite.log.annotation.EnableRabbitMQLog;
//import com.aio.portable.park.beanprocessor.CustomImportBeanDefinitionRegistrar;
//import com.aio.portable.park.ToMapTest;
//import com.aio.portable.park.other.jvm.MetaspaceTest;
//import com.aio.portable.park.task.ThreadLocalTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
//        DruidDataSourceAutoConfigure.class,
//        MybatisAutoConfiguration.class,
        KafkaAutoConfiguration.class,
        RabbitAutoConfiguration.class,
}, scanBasePackages = "com.aio.portable")
//@EntityScan("com.aio.portable")

//@ComponentScan(lazyInit = true)
//@DependsOn(PropertyBean.KAFKA_PROPERTIES)
// -javaagent:./jagent/target/jagent-1.1.4-SNAPSHOT.jar=Hello
//@Import(LogRabbitMQProperties.class)
//@DependsOn(PropertyBean.RABBITMQ_PROPERTIES)
@EnableRabbitMQLog
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
