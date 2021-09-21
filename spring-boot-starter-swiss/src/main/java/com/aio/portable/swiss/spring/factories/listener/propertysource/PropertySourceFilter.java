package com.aio.portable.swiss.spring.factories.listener.propertysource;

import org.springframework.core.env.PropertySource;

import java.util.function.Predicate;

public interface PropertySourceFilter extends Predicate<PropertySource<?>> {
}
