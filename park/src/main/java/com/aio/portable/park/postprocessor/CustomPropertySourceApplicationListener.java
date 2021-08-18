package com.aio.portable.park.postprocessor;

import com.aio.portable.swiss.factories.listener.PropertySourceApplicationListener;
import com.aio.portable.swiss.suite.bean.type.KeyValuePair;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
public class CustomPropertySourceApplicationListener extends PropertySourceApplicationListener {
    @Override
    public KeyValuePair<String, Object> replace(String key, Object value) {
            // swagger.api-info.title
            if (key.equals("swagger.api-info.title"))
                return new KeyValuePair<>(key, value + new Date().toString());
            if (value.equals("www.taoche.com"))
                return new KeyValuePair<>(key, value + new Date().toString());
            else
                return new KeyValuePair<>(key, value);
    }


}
