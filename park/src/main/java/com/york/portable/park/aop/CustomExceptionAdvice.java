package com.york.portable.park.aop;

import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;
import com.york.portable.swiss.hamlet.interceptor.HamletExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Import(CustomLogHubFactory.class)
@Configuration
public class CustomExceptionAdvice extends HamletExceptionAdvice {

    public CustomExceptionAdvice(LogHubFactory customLogHubFactory) {
        super(customLogHubFactory);
    }

}
