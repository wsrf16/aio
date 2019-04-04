package com.york.portable.park.aop;

import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import com.york.portable.swiss.hamlet.ExceptionAdvice;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class CustomExceptionAdvice extends ExceptionAdvice {

    public CustomExceptionAdvice(ILoggerHubFactory loggerHubFactory) {
        super(loggerHubFactory);
    }

}
