package com.york.portable.park.aop;

import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;
import com.york.portable.swiss.hamlet.interceptor.log.HamletWebLogAspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends HamletWebLogAspect {
    public CustomWebLogAspect(LogHubFactory customLogHubFactory) {
        super(customLogHubFactory);
    }

    private final static String POINTCUT = "execution(public * com.york.portable.park.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

//    @Pointcut(POINTCUT)
    @Pointcut(POINTCUT_SPECIAL)
    public void webLog() {
    }
}
