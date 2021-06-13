package com.aio.portable.swiss.suite.properties;

import org.springframework.core.env.PropertySource;

import java.util.function.Predicate;

public interface PropertySourceMatcher extends Predicate<PropertySource<?>> {
}
