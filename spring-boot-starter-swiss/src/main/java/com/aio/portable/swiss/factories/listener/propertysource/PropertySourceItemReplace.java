package com.aio.portable.swiss.factories.listener.propertysource;

import com.aio.portable.swiss.suite.bean.type.KeyValuePair;

import java.util.function.BiFunction;

public interface PropertySourceItemReplace extends BiFunction<String, Object, KeyValuePair<String, Object>> {

}
