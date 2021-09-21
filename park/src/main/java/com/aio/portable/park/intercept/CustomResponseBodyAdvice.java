package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.HamletResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@RestControllerAdvice
public class CustomResponseBodyAdvice extends HamletResponseBodyAdvice {
    public CustomResponseBodyAdvice(WebMvcConfigurationSupport webMvcConfigurationSupport) {
        super(webMvcConfigurationSupport);
    }
}
