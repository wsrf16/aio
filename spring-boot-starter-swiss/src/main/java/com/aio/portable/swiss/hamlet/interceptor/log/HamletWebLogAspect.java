package com.aio.portable.swiss.hamlet.interceptor.log;

import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public abstract class HamletWebLogAspect extends AbstractWebLogAspect {
    public HamletWebLogAspect(LogHubFactory logHubFactory) {
        super(logHubFactory);
    }

    @Pointcut(POINTCUT_SPECIAL)
    public abstract void webLog();

//    @Before("webLog()")
    public void doBefore(ProceedingJoinPoint joinPoint) {
        super.doBefore(joinPoint);
    }

//    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(ProceedingJoinPoint joinPoint, Object result) {
        super.doAfterReturning(joinPoint, result);
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return super.doAround(joinPoint);
    }

}
