package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.classic.HamletResponseBodyAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@RestControllerAdvice
public class CustomResponseBodyAdvice extends HamletResponseBodyAdvice {
    public CustomResponseBodyAdvice(WebMvcConfigurationSupport webMvcConfigurationSupport) {
        super(webMvcConfigurationSupport);
    }
}
