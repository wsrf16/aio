package com.aio.portable.swiss.hamlet.interceptor.classic.annotation.sign;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.interceptor.AnnotationInterceptor;
import com.aio.portable.swiss.sugar.location.URLSugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.encode.HashConvert;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.IOSugar;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class HamletSignatureAnnotationInterceptor extends AnnotationInterceptor {
    @Override
    protected List<Class<? extends Annotation>> annotations() {
        return Arrays.asList(RequiredSign.class);
    }

    protected long expiredMinutes() {
        return 60 * 24;
    }

    @Override
    protected boolean verify(HttpServletRequest request, HttpServletResponse response, Object handler, Method method, Annotation methodAnnotation, Class<?> clazz, Annotation clazzAnnotation) {
        String computed = validAndSign(request);
        String sign = getSignOfHeaders(request);
        verify(computed, sign);
        return true;
    }

    private String getSignOfHeaders(HttpServletRequest request) {
        String sign = request.getHeader("sign");
        return sign;
    }

    private void verify(String computed, String sign) {
        if (!Objects.equals(computed, sign))
            throw new BizException(BaseBizStatusEnum.staticUnauthorized().getCode(), "请求无效！");
    }

    private final String validAndSign(HttpServletRequest request) {
        String queryString = request.getQueryString();
        ServletInputStream inputStream = getServletInputStream(request);
        String body = IOSugar.Streams.toString(inputStream);

        StringBuffer sb = new StringBuffer();
        boolean hasTimestamp = false;
        if (StringUtils.hasText(queryString)) {
            sb.append(queryString);

            Map<String, Object> map = URLSugar.convertQueryStringToMap(queryString);
            if (map.containsKey("timestamp")) {
                hasTimestamp = true;
                int timestamp = Integer.valueOf((String) map.get("timestamp"));
                validTimestamp(timestamp);
            }
        }
        if (StringUtils.hasText(body)) {
            sb.append(body);

            JsonNode parser = JacksonSugar.parse(body).get("timestamp");
            if (parser != null) {
                hasTimestamp = true;
                int timestamp = parser.asInt();
                validTimestamp(timestamp);
            }
        }
        if (!hasTimestamp)
            throw new BizException(BaseBizStatusEnum.staticUnauthorized().getCode(), "请求无效！");

        String s = sb.toString();
        String sha1 = encode(s);
        return sha1;
    }

    protected String encode(String s) {
        String sha1 = HashConvert.SHA1.encodeToBase64(s);
        return sha1;
    }

    private void validTimestamp(int timestamp) {
        LocalDateTime start = DateTimeSugar.UnixTime.convertUnix2DateTime(timestamp);
        LocalDateTime end = DateTimeSugar.LocalDateTimeUtils.now();
        Duration duration = DateTimeSugar.LocalDateTimeUtils.between(start, end);
        if (duration.toMinutes() > expiredMinutes())
            throw new BizException(BaseBizStatusEnum.staticUnauthorized().getCode(), "请求过期失效！");
    }

    private static final ServletInputStream getServletInputStream(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            return inputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
