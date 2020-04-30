package com.aio.portable.swiss.suite.log.annotation;


import com.aio.portable.swiss.suite.log.classic.properties.PropertyBean;
import org.springframework.context.annotation.DependsOn;

import java.lang.annotation.*;

//@Import({RedisRepositoriesRegistrar.class})
@DependsOn(PropertyBean.RABBITMQ_PROPERTIES)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface InitialRabbitMQLogProperties {
}
