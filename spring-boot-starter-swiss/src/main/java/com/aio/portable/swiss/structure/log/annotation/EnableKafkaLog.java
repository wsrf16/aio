package com.aio.portable.swiss.structure.log.annotation;


import com.aio.portable.swiss.structure.log.base.classic.properties.PropertyBean;
import org.springframework.context.annotation.DependsOn;

import java.lang.annotation.*;

//@Import({RedisRepositoriesRegistrar.class})
@DependsOn(PropertyBean.KAFKA_PROPERTIES)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Inherited
public @interface EnableKafkaLog {
}
