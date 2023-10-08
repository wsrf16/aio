package com.aio.portable.swiss.spring;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;

public class SpringController {

    private static Class<?> registryClazz = ClassLoaderSugar.load("org.springframework.web.servlet.handler.AbstractHandlerMethodMapping$MappingRegistry");

    private static Class<?> mappingClazz = AbstractHandlerMethodMapping.class;

    public static final MultiValueMap<String, Object> urlLookup(AbstractHandlerMethodMapping abstractHandlerMethodMapping) {
        Object mappingRegistry = ClassSugar.invoke(abstractHandlerMethodMapping, mappingClazz, "getMappingRegistry");

        MultiValueMap<String, Object> urlLookup = ClassSugar.getDeclaredFieldValue(mappingRegistry, registryClazz, "urlLookup");
        return urlLookup;
    }

    public static final void registerMapping(AbstractHandlerMethodMapping mappingHandlerMapping, Object controllerInstance, Method method, String... patterns) {
        RequestMappingInfo info = new RequestMappingInfo(
                new PatternsRequestCondition(patterns),
                new RequestMethodsRequestCondition(),
                null, null, null, null, null);
        mappingHandlerMapping.registerMapping(info, controllerInstance, method);
    }

}
