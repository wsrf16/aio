package com.aio.portable.swiss.suite.properties;

import java.util.function.BiFunction;

public interface PropertySourceResolver extends BiFunction<String, Object, Object> {

}
