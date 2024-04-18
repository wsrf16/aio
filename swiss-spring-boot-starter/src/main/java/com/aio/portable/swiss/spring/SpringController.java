package com.aio.portable.swiss.spring;

import com.aio.portable.swiss.sugar.location.UrlSugar;
import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class SpringController {

    private static Class<?> mappingClazz = AbstractHandlerMethodMapping.class;

    private static Class<?> registryClazz = ClassLoaderSugar.load("org.springframework.web.servlet.handler.AbstractHandlerMethodMapping$MappingRegistry");

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

    public static final ResponseEntity<Object> forward(HttpServletRequest request, String shimContextPath, RestTemplate restTemplate) {
//        String contextPath = request.getContextPath();
//        if (!StringUtils.isEmpty(contextPath))
//            contextPath = UrlSugar.concat(contextPath, shimContextPath);
//
//        contextPath = StringUtils.isEmpty(contextPath) ? shimContextPath : UrlSugar.concat(contextPath, shimContextPath);

        ResponseEntity<Object> responseEntity = RestTemplater.create(restTemplate)
                .forward(request, shimContextPath, Object.class);
        return responseEntity;
    }
}
