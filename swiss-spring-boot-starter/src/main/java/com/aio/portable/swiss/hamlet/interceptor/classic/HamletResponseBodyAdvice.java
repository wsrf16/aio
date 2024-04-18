package com.aio.portable.swiss.hamlet.interceptor.classic;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
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
public abstract class HamletResponseBodyAdvice implements ResponseBodyAdvice<Object>, InitializingBean {

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
            ResponseWrapper responseWrapper = ResponseWrappers.build(BaseBizStatusEnum.staticSucceed().getCode(), BaseBizStatusEnum.staticSucceed().getMessage(), body);

            HttpHeaders headers = serverHttpResponse.getHeaders();
            boolean containsKey = headers.containsKey(ResponseWrapper.SPAN_ID_HEADER);
            if (containsKey) {
                String spanId = headers.get(ResponseWrapper.SPAN_ID_HEADER).get(0);
                responseWrapper.setSpanId(spanId);
            }
            return responseWrapper;
        } else {
            return body;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setPriorityHighest();
    }

    private void setPriorityHighest() throws Exception {
        List<HttpMessageConverter<?>> converters = parseHttpMessageConverters();
        List<HttpMessageConverter<?>> jacksonHttpMessageConverters = parseJacksonHttpMessageConverters(converters);
        setPriorityHighest(converters, jacksonHttpMessageConverters);
    }

    private void setPriorityHighest(List<HttpMessageConverter<?>> converters, List<HttpMessageConverter<?>> highest) throws Exception {
        if (!highest.contains(converters.get(0))) {
            converters.removeAll(highest);
            converters.addAll(0, highest);
        }
    }

    private List<HttpMessageConverter<?>> parseHttpMessageConverters() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = BeanSugar.Methods.getDeclaredMethodIncludeParents(WebMvcConfigurationSupport.class, "getMessageConverters");
        method.setAccessible(true);
        List<HttpMessageConverter<?>> converters = (List<HttpMessageConverter<?>>) method.invoke(webMvcConfigurationSupport);
        return converters;
    }

    private List<HttpMessageConverter<?>> parseJacksonHttpMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> converterList = converters.stream().filter(c -> c instanceof MappingJackson2HttpMessageConverter).collect(Collectors.toList());
        return converterList;
    }
}
