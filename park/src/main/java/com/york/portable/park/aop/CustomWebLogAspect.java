package com.york.portable.park.aop;

import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;
import com.york.portable.swiss.hamlet.WebLogAspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends WebLogAspect {
    public CustomWebLogAspect(LoggerHubFactory customLoggerHubFactory) {
        super(customLoggerHubFactory);
    }

    private final static String POINTCUT = "execution(public * com.york.portable.park.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

//    @Pointcut(POINTCUT)
    @Pointcut(POINTCUT_SPECIAL)
    public void webLog() {
    }
}
