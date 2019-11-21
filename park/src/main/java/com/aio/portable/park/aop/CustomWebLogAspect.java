package com.aio.portable.park.aop;

import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;
import com.aio.portable.swiss.hamlet.interceptor.log.HamletWebLogAspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends HamletWebLogAspect {
    public CustomWebLogAspect(LogHubFactory logFactory) {
        super(logFactory);
    }

    private final static String POINTCUT = "execution(public * com.aio.portable.park.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

//    @Pointcut(POINTCUT)
    @Pointcut(POINTCUT_SPECIAL)
    public void webLog() {
    }
}
