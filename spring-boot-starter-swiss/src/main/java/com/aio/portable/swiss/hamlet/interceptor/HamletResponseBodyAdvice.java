package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

//@RestControllerAdvice
public class HamletResponseBodyAdvice implements ResponseBodyAdvice<Object>, InitializingBean {

    private final WebMvcConfigurationSupport webMvcConfigurationSupport;

    public HamletResponseBodyAdvice(WebMvcConfigurationSupport webMvcConfigurationSupport) {
        this.webMvcConfigurationSupport = webMvcConfigurationSupport;
    }

    @Override
    public boolean supports(MethodParameter responseBean, Class convertClass) {
        return !responseBean.getMethod().getReturnType().isAssignableFrom(Void.class)
                && !responseBean.getMethod().getReturnType().isAssignableFrom(ResponseWrapper.class)
                && !responseBean.getContainingClass().getPackage().getName().startsWith("springfox.documentation");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter responseBean, MediaType mediaType, Class convertClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body == null || !(body instanceof ResponseWrapper)) {
//            mediaType = MediaType.APPLICATION_JSON;
//            convertClass = MappingJackson2HttpMessageConverter.class;
            ResponseWrapper responseWrapper = ResponseWrapper.build(BaseBizStatusEnum.staticSucceed().getCode(), BaseBizStatusEnum.staticSucceed().getMessage(), body);
            return responseWrapper;
        } else {
            return body;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HttpMessageConverter<?>> converters = getHttpMessageConverters();
//        Jackson2ObjectMapperBuilder json = Jackson2ObjectMapperBuilder.json();
//        json.applicationContext(SpringContextHolder.getApplicationContext());

        List<HttpMessageConverter<?>> converterList = getJacksonHttpMessageConverters(converters);
        converters.removeAll(converterList);
        converters.addAll(0, converterList);
    }

    private List<HttpMessageConverter<?>> getHttpMessageConverters() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = BeanSugar.Methods.getDeclaredMethodIncludeParents(WebMvcConfigurationSupport.class, "getMessageConverters");
        method.setAccessible(true);
        List<HttpMessageConverter<?>> converters = (List<HttpMessageConverter<?>>) method.invoke(webMvcConfigurationSupport);
        return converters;
    }

    private List<HttpMessageConverter<?>> getJacksonHttpMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> converterList = converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).collect(Collectors.toList());
        return converterList;
    }
}
