package com.aio.portable.swiss.spring.factories.processor.propertysource.convert;

import org.springframework.core.env.PropertySource;

import java.util.function.Function;

public interface PropertySourceConvert extends Function<PropertySource<?>, PropertySource<?>> {

}
