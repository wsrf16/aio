package com.aio.portable.swiss.suite.log.annotation;


import com.aio.portable.swiss.suite.log.impl.PropertyBean;
import org.springframework.context.annotation.DependsOn;

import java.lang.annotation.*;

//@Import({RedisRepositoriesRegistrar.class})
@DependsOn(PropertyBean.KAFKA_LOG_PROPERTIES)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface InitialKafkaLogProperties {
}
