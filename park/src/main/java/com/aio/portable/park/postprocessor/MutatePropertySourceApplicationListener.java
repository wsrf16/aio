package com.aio.portable.park.postprocessor;

import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class MutatePropertySourceApplicationListener extends com.aio.portable.swiss.factories.context.MutatePropertySourceApplicationListener {
    @Override
    public Object resolve(String name, Object value) {
            // swagger.api-info.title
            if (name.equals("swagger.api-info.title"))
                return value + new Date().toString();
            if (value.equals("www.taoche.com"))
                return value + new Date().toString();
            else
                return value;
    }
}
