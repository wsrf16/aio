package com.aio.portable.swiss.suite.storage.db.freedatasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.text.MessageFormat;

public abstract class FreeDataSourceAspect {
    private static final Log log = LogFactory.getLog(FreeDataSourceAspect.class);
    public static final String POINTCUT = "pointcut()";

    @Pointcut("@annotation(com.aio.portable.swiss.suite.storage.db.freedatasource.TargetDataSource)" +
            " || @within(com.aio.portable.swiss.suite.storage.db.freedatasource.TargetDataSource)")
    public void pointcut() {
    }


//    @Before(value = POINTCUT)
    public void before(JoinPoint point) throws NoSuchMethodException {
        Object target = point.getTarget();
        Signature signature = point.getSignature();

        String methodName = signature.getName();
        Class<?> clazz = target.getClass().getInterfaces()[0];
        Class<?>[] parameterTypes = ((MethodSignature) signature).getMethod().getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);

        if (method != null) {
            TargetDataSource targetDataSource;
            if (method.isAnnotationPresent(TargetDataSource.class)) {
                targetDataSource = method.getAnnotation(TargetDataSource.class);
                DataSourceHolder.putDataSource(targetDataSource.value());
                log.debug(MessageFormat.format("FreeDataSourceAspect：signature-{0}, targetDataSource-{1}", signature.toString(), targetDataSource.value()));
            } else if (clazz.isAnnotationPresent(TargetDataSource.class)
                    && !method.isAnnotationPresent(TargetDataSource.class)) {
                targetDataSource = clazz.getAnnotation(TargetDataSource.class);
                DataSourceHolder.putDataSource(targetDataSource.value());
                log.debug(MessageFormat.format("FreeDataSourceAspect：signature-{0}, targetDataSource-{1}", signature.toString(), targetDataSource.value()));
            }
        }
    }


    private static final String DEFAULT_DATASOURCE_KEY = "default";
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        before(proceedingJoinPoint);
        Object result = proceedingJoinPoint.proceed();
//        DataSourceHolder.clearDataSourceType();
        DataSourceHolder.putDataSource(DEFAULT_DATASOURCE_KEY);
        return result;
    }
}
