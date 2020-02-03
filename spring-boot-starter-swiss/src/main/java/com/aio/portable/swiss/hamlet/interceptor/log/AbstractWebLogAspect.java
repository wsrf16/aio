package com.aio.portable.swiss.hamlet.interceptor.log;

import com.aio.portable.swiss.hamlet.exception.HandOverException;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;
import com.aio.portable.swiss.structure.log.base.factory.LogHubPool;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.hamlet.bean.RequestRecord;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

class AbstractWebLogAspect {
    public AbstractWebLogAspect(LogHubFactory logHubFactory) {
        loggerPool = LogHubPool.getSingleton(logHubFactory);
    }

    protected static LogHubPool loggerPool;
    protected static String REQUEST_SUMMARY = "接口请求";
    protected static String RESPONSE_SUMMARY = "接口响应";
    protected static String EXCEPTION_SUMMARY = "接口异常";

    protected final static String POINTCUT_CONTROLLER = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected final static String POINTCUT_MAPPING = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.Mapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected final static String LOG_MARKER_TYPENAME = "com.aio.portable.swiss.structure.log.annotation.LogMarker";
    protected final static String LOG_MARKER_EXCEPT_TYPENAME = "com.aio.portable.swiss.structure.log.annotation.LogMarkerExcept";

    protected final static String POINTCUT_SPECIAL = "" +
            "(@within(" + LOG_MARKER_TYPENAME + ")" + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")" +
            " && (" + POINTCUT_MAPPING + "))" +
            " || @annotation("+ LOG_MARKER_TYPENAME +")";

    public void doBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestRecord requestRecord = createRequestRecord(request, joinPoint);

        LogHub logger = loggerPool.putIfAbsent(joinPoint.getSignature().getDeclaringTypeName());
        if (logger != null) {
            logger.info(REQUEST_SUMMARY, requestRecord);
        }
    }

    public void doAfterReturning(ProceedingJoinPoint joinPoint, Object result) {
        LogHub logger = loggerPool.putIfAbsent(joinPoint.getSignature().getDeclaringTypeName());
        if (logger != null) {
            logger.info(RESPONSE_SUMMARY, result);
        }
    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        RequestRecord requestRecord = createRequestRecord(request, joinPoint);

        LogHub logger = loggerPool.putIfAbsent(joinPoint.getSignature().getDeclaringTypeName());
        String traceId = generateUniqueId();
        if (logger != null) {
            logger.info(MessageFormat.format("{0}({1})", REQUEST_SUMMARY, traceId), requestRecord);
        }

        Object responseRecord = null;
        try {
            responseRecord = joinPoint.proceed();
            if (responseRecord instanceof ResponseWrapper) {
                ((ResponseWrapper) responseRecord).setTraceId(traceId);
            }

            if (logger != null) {
                logger.info(MessageFormat.format("{0}({1})", RESPONSE_SUMMARY, traceId), responseRecord);
            }
        } catch (Exception e) {
            logger.e(MessageFormat.format("{0}({1})", EXCEPTION_SUMMARY, traceId), requestRecord, e);
            HandOverException handOverException = new HandOverException(e, requestRecord, traceId);
            throw handOverException;
        }
        return responseRecord;
    }

    private static String generateUniqueId() {
        return UUID.randomUUID().toString().replace("-", Constant.EMPTY);
    }

    private static RequestRecord createRequestRecord(HttpServletRequest request, JoinPoint joinPoint) {
        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setRemoteAddress(request.getRemoteAddr());
        String url = request.getRequestURL().toString() + (!StringUtils.hasText(request.getQueryString()) ? Constant.EMPTY :  "?" + request.getQueryString());
        requestRecord.setRequestURL(url);
        requestRecord.setHttpMethod(request.getMethod());
        requestRecord.setHeaders(parseHeader(request));
        requestRecord.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        Object[] args = filterArguments(joinPoint.getArgs());
        requestRecord.setArguments(args);
        return requestRecord;
    }

    private static Object[] filterArguments(Object[] args) {
        Object[] newArgs = Arrays.stream(args).filter(c -> filterArgumentsCondition(c)).collect(Collectors.toList()).toArray();
        return newArgs;
    }

    private static boolean filterArgumentsCondition(Object arg) {
        boolean b = arg != null;
        b = b && !(arg instanceof HttpServletRequest);
        b = b && !(arg instanceof HttpServletResponse);
        b = b && !(arg instanceof BindingResult);
        return b;
    }

    private static Map<String, String> parseHeader(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        Enumeration<String> nameEnumeration = request.getHeaderNames();
        while(nameEnumeration.hasMoreElements()){
            String name = nameEnumeration.nextElement();
            String value = request.getHeader(name);
            map.put(name, value);
        }
        return map;
    }
}
