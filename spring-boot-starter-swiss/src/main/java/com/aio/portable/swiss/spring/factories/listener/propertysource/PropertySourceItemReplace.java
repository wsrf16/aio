package com.aio.portable.swiss.spring.factories.listener.propertysource;

import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;

import java.util.function.BiFunction;

public interface PropertySourceItemReplace extends BiFunction<String, Object, KeyValuePair<String, Object>> {

}
