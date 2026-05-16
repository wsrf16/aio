package com.aio.portable.swiss.hamlet.interceptor.classic.log;

import com.aio.portable.swiss.hamlet.bean.ResponseBeans;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecord;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord;
import com.aio.portable.swiss.spring.RequestContextHolderSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.factory.LogHubPool;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecordSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.MessageFormat;

class AbstractWebLogAspect {
    public AbstractWebLogAspect() {
        logHubPool = LogHubPool.buildLogHubPool();
    }

    public AbstractWebLogAspect(LogHubFactory logHubFactory) {
        logHubPool = LogHubPool.buildLogHubPool(logHubFactory);
    }

    protected static LogHubPool logHubPool;
    protected static String REQUEST_SUMMARY = "Input Parameters";
    protected static String RESPONSE_SUMMARY = "Output Parameters";
    protected static String EXCEPTION_SUMMARY = "Exception Information";

    protected static final String POINTCUT_CONTROLLER =
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected static final String POINTCUT_MAPPING =
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.Mapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    // LogRecord.class.getName();
    protected static final String LOG_MARKER_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord";
    // LogRecordIgnore.class.getName();
    protected static final String LOG_MARKER_EXCEPT_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecordIgnore";

    protected static final String POINTCUT_SPECIAL_MAPPING =
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")"
            + " && (" + POINTCUT_MAPPING + ")"
            + ")"
            + " || @annotation("+ LOG_MARKER_TYPENAME +")";


    protected static final String POINTCUT_SPECIAL =
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")"
            + ")"
            + " || @annotation("+ LOG_MARKER_TYPENAME +")";

//    public void doBefore(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        RequestRecord requestRecord = RequestRecord.newInstance(request, joinPoint);
//
//        LogHub logger = loggerPool.get(joinPoint.getSignature().getDeclaringTypeName());
//        if (logger != null) {
//            logger.info(REQUEST_SUMMARY, requestRecord);
//        }
//    }
//
//    public void doAfterReturning(JoinPoint joinPoint, Object result) {
//        LogHub logger = loggerPool.get(joinPoint.getSignature().getDeclaringTypeName());
//        if (logger != null) {
//            logger.info(RESPONSE_SUMMARY, result);
//        }
//    }

    public boolean enableAroundLog() {
        return true;
    }

    public boolean enableThrowingLog() {
        return true;
    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        if (joinPoint.getTarget() != null) {
//            Class<?> clazz = joinPoint.getTarget().getClass();
//            if (clazz.isAnnotationPresent(LogRecord.class)) {
//                LogRecord annotation = clazz.getAnnotation(LogRecord.class);
//                if (annotation.ignore() == false)
//                    return joinPoint.proceed();
//            }
//        }

        HttpServletRequest request = RequestContextHolderSugar.getRequest();
        HttpServletResponse response = RequestContextHolderSugar.getResponse();

        RequestRecord requestRecord = RequestRecordSession.injectRequestRecord(request, joinPoint);
//        String spanId = requestRecord.getSpanId();

        LogHub log = logHubPool.get(joinPoint.getSignature().getDeclaringTypeName());
        RequestRecord.addHeader(response, requestRecord);

        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            Method method = ((MethodSignature) signature).getMethod();
            if (method.isAnnotationPresent(LogRecord.class)) {
                LogRecord annotation = method.getAnnotation(LogRecord.class);
                if (!annotation.enabled()) {
//                    return joinPoint.proceed();
                    Object responseEntity = joinPoint.proceed();
                    ResponseBeans.fillHeader(responseEntity, requestRecord);
                    return responseEntity;
                }
            }
        }

        Class<?> clazz = signature.getDeclaringType();
        if (clazz.isAnnotationPresent(LogRecord.class)) {
            LogRecord annotation = clazz.getAnnotation(LogRecord.class);
            if (!annotation.enabled()) {
//                return joinPoint.proceed();
                Object responseEntity = joinPoint.proceed();
                ResponseBeans.fillHeader(responseEntity, requestRecord);
                return responseEntity;
            }
        }

        try {
            String globalId = requestRecord.getGlobalId();
            String requestSummary = MessageFormat.format("{0}({1})", REQUEST_SUMMARY, globalId);
            String responseSummary = MessageFormat.format("{0}({1})", RESPONSE_SUMMARY, globalId);

            if (log != null && enableAroundLog()) {
                log.i(requestSummary, requestRecord);
            }

            Object responseEntity = joinPoint.proceed();
            ResponseBeans.fillHeader(responseEntity, requestRecord);

            if (log != null && enableAroundLog()) {
                log.i(responseSummary, responseEntity);
            }

            return responseEntity;
        } catch (Exception e) {
            RequestRecordSession.setException(e);
            if (log != null && enableThrowingLog()) {
                String globalId = requestRecord.getGlobalId();
                String summary = MessageFormat.format("{0}({1})", EXCEPTION_SUMMARY, globalId);
                log.e(summary, requestRecord, e);
            }
            throw e;
        }
    }

}
