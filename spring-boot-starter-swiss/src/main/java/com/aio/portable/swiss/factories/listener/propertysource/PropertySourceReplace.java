package com.aio.portable.swiss.factories.listener.propertysource;

import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;
import java.util.function.Consumer;

public interface PropertySourceReplace extends Consumer<PropertySource<?>> {

}
