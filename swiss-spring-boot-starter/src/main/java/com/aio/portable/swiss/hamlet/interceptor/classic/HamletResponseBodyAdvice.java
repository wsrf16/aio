package com.aio.portable.swiss.hamlet.interceptor.classic;

import com.aio.portable.swiss.hamlet.bean.ResponseBean;
import com.aio.portable.swiss.hamlet.bean.ResponseStatuses;
import com.aio.portable.swiss.hamlet.bean.ResponseBeans;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
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

import java.util.List;

//@RestControllerAdvice
public abstract class HamletResponseBodyAdvice implements ResponseBodyAdvice<Object>, InitializingBean {

    private final WebMvcConfigurationSupport webMvcConfigurationSupport;

    public HamletResponseBodyAdvice(WebMvcConfigurationSupport webMvcConfigurationSupport) {
        this.webMvcConfigurationSupport = webMvcConfigurationSupport;
    }

    @Override
    public boolean supports(MethodParameter responseBean, Class convertClass) {
        return !responseBean.getMethod().getReturnType().isAssignableFrom(Void.class)
                && !responseBean.getMethod().getReturnType().isAssignableFrom(ResponseBean.class)
                && !responseBean.getContainingClass().getPackage().getName().startsWith("springfox.documentation");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter responseBean, MediaType mediaType, Class convertClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body == null || !(body instanceof ResponseBean)) {
//            mediaType = MediaType.APPLICATION_JSON;
//            convertClass = MappingJackson2HttpMessageConverter.class;
            ResponseBean<?> responseWrapper = ResponseBeans.build(ResponseStatuses.staticSucceed().getCode(), ResponseStatuses.staticSucceed().getMessage(), body);

            HttpHeaders headers = serverHttpResponse.getHeaders();
            boolean containsKey = headers.containsKey(ResponseBean.SPAN_ID_HEADER);
            if (containsKey) {
                String spanId = headers.get(ResponseBean.SPAN_ID_HEADER).get(0);
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

    private void setPriorityHighest() {
        List<HttpMessageConverter<?>> converters = ClassSugar.Methods.invoke(webMvcConfigurationSupport, "getMessageConverters");
        CollectionSugar.moveTo(converters, 0, c -> c instanceof MappingJackson2HttpMessageConverter);
    }

}
