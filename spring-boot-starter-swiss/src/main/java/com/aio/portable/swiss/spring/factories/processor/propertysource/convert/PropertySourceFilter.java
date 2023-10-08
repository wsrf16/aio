package com.aio.portable.swiss.spring.factories.processor.propertysource.convert;

import org.springframework.core.env.PropertySource;

import java.util.function.Predicate;

public interface PropertySourceFilter extends Predicate<PropertySource<?>> {
    PropertySourceFilter ALL = s -> true;
}
