package com.york.portable.swiss.hamlet;

import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubPool;
import com.york.portable.swiss.hamlet.model.RequestRecord;
import com.york.portable.swiss.hamlet.model.ResponseWrapper;
import com.york.portable.swiss.hamlet.model.exception.BizException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

//@Component
@Aspect
public abstract class WebLogAspect {

    public WebLogAspect(LoggerHubFactory loggerHubFactory) {
        loggerPool = LoggerHubPool.newInstance(loggerHubFactory);
    }

    private static LoggerHubPool loggerPool;
    protected static String REQUEST_SUMMARY = "切面记录请求";
    protected static String RESPONSE_SUMMARY = "切面记录返回";
    protected static String EXCEPTION_SUMMARY = "切面记录异常";


//    private final static String POINTCUT = "execution(public * com.york.portable.park.controller..*.*(..)) && (@annotation(org.springframework.web.bind.annotation.GetMapping) || @annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.RequestMapping))";

    protected final static String POINTCUT_CONTROLLER = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected final static String POINTCUT_MAPPING = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PatchMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.Mapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected final static String POINTCUT_SPECIAL = "" +
            "(@within(com.york.portable.swiss.assist.log.annotation.LogMarker) " +
            "&& !@annotation(com.york.portable.swiss.assist.log.annotation.LogMarkerExcept) " +
            "&& (" + POINTCUT_MAPPING + "))" +

            " || @annotation(com.york.portable.swiss.assist.log.annotation.LogMarker)";


    @Pointcut(POINTCUT_SPECIAL)
    public abstract void webLog();

    //    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setRemoteAddress(request.getRemoteAddr());
        String url = StringUtils.isBlank(request.getQueryString()) ? request.getRequestURL().toString() : request.getRequestURL().toString() + "?" + request.getQueryString();
        requestRecord.setRequestURL(url);
        requestRecord.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        requestRecord.setHttpMethod(request.getMethod());
//        String taskId = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
//        requestRecord.setUniqueId(taskId);
        Object[] args = filterArguments(joinPoint.getArgs());
        requestRecord.setArguments(args);
        LoggerHubImp logger = loggerPool.putIfAbsent(joinPoint.getSignature().getDeclaringTypeName());
        if (logger != null) {
            logger.info(REQUEST_SUMMARY, requestRecord);
        }
    }

    private static Object[] filterArguments(Object[] args) {
        Object[] newArgs = Arrays.stream(args).filter(c -> filterArgumentsCondition(c)).collect(Collectors.toList()).toArray();
        return newArgs;
    }

    private static boolean filterArgumentsCondition(Object arg) {
        boolean b = arg != null;
        b = b && !(arg instanceof HttpServletRequest);
        b = b && !(arg instanceof HttpServletResponse);
        return b;
    }

    //    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        LoggerHubImp logger = loggerPool.putIfAbsent(joinPoint.getSignature().getDeclaringTypeName());
        if (logger != null) {
            logger.info(RESPONSE_SUMMARY, result);
        }
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String uniqueId = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setRemoteAddress(request.getRemoteAddr());
        String url = StringUtils.isBlank(request.getQueryString()) ? request.getRequestURL().toString() : request.getRequestURL().toString() + "?" + request.getQueryString();
        requestRecord.setRequestURL(url);
        requestRecord.setClassMethod(proceedingJoinPoint.getSignature().getDeclaringTypeName() + "." + proceedingJoinPoint.getSignature().getName());
        requestRecord.setHttpMethod(request.getMethod());

        Object[] args = filterArguments(proceedingJoinPoint.getArgs());
        requestRecord.setArguments(args);
        LoggerHubImp logger = loggerPool.putIfAbsent(proceedingJoinPoint.getSignature().getDeclaringTypeName());
        if (logger != null) {
            logger.info(MessageFormat.format("{0}({1})", REQUEST_SUMMARY, uniqueId), requestRecord);
        }

        Object responseRecord = null;
        try {
            responseRecord = proceedingJoinPoint.proceed();
            if (responseRecord instanceof ResponseWrapper) {
                ((ResponseWrapper) responseRecord).setUniqueId(uniqueId);
            }

            if (logger != null) {
                logger.info(MessageFormat.format("{0}({1})", RESPONSE_SUMMARY, uniqueId), responseRecord);
            }
        } catch (Exception e) {
            logger.e(MessageFormat.format("{0}({1})", EXCEPTION_SUMMARY, uniqueId), requestRecord, e);
        }
        return responseRecord;
    }
}
