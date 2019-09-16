package com.aio.portable.park.aop;

import com.aio.portable.park.common.LogFactory;
import com.aio.portable.swiss.assist.log.hub.factory.LogHubFactory;
import com.aio.portable.swiss.hamlet.interceptor.HamletExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Import(LogFactory.class)
@Configuration
public class CustomExceptionAdvice extends HamletExceptionAdvice {

    public CustomExceptionAdvice(LogHubFactory logFactory) {
        super(logFactory);
    }

}
