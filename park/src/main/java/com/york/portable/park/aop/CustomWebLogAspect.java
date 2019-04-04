package com.york.portable.park.aop;

import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import com.york.portable.swiss.hamlet.WebLogAspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends WebLogAspect {
    public CustomWebLogAspect(ILoggerHubFactory loggerHubFactory) {
        super(loggerHubFactory);
    }

    private final static String POINTCUT = "execution(public * com.york.portable.park.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

    @Pointcut(POINTCUT)
    public void webLog() {
    }
}
