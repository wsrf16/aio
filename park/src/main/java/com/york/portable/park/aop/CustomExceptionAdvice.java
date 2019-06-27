package com.york.portable.park.aop;

import com.york.portable.park.common.log.CustomLoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;
import com.york.portable.swiss.hamlet.ExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Import(CustomLoggerHubFactory.class)
@Configuration
public class CustomExceptionAdvice extends ExceptionAdvice {

    public CustomExceptionAdvice(LoggerHubFactory customLoggerHubFactory) {
        super(customLoggerHubFactory);
    }

}
