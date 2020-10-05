package com.aio.portable.swiss.suite.log.annotation;


import com.aio.portable.swiss.suite.log.impl.PropertyBean;
import org.springframework.context.annotation.DependsOn;

import java.lang.annotation.*;

//@Import({RedisRepositoriesRegistrar.class})
//@DependsOn(PropertyBean.LOG_PROPERTIES)
@DependsOn({PropertyBean.RABBITMQ_LOG_PROPERTIES, PropertyBean.KAFKA_LOG_PROPERTIES})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
@interface InitialLogProperties {
}
