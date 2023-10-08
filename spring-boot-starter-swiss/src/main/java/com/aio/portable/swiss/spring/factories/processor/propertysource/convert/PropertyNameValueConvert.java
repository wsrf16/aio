package com.aio.portable.swiss.spring.factories.processor.propertysource.convert;

import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.core.env.PropertySource;

import java.util.function.BiFunction;

public interface PropertyNameValueConvert extends BiFunction<PropertySource<?>, KeyValuePair<String, Object>, Object> {
    PropertyNameValueConvert NO_OP = (propertySource, keyValuePair) -> keyValuePair.getValue();
}
