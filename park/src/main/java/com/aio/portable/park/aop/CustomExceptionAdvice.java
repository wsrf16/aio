package com.aio.portable.park.aop;

import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;
import com.aio.portable.swiss.hamlet.interceptor.HamletExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionAdvice extends HamletExceptionAdvice {

    public CustomExceptionAdvice(LogHubFactory appLogHubFactory) {
        super(appLogHubFactory);
    }

}
