package com.aio.portable.park.intercept;

import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.hamlet.interceptor.log.HamletWebLogAspect;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CustomWebLogAspect extends HamletWebLogAspect {
    public CustomWebLogAspect(LogHubFactory logHubFactory) {
        super(logHubFactory);
    }

    @Pointcut(POINTCUT_SPECIAL)
    public void webLog() {
    }
}
