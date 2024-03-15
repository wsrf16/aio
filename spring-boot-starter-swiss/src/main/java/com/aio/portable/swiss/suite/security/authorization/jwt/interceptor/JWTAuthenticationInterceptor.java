package com.aio.portable.swiss.suite.security.authorization.jwt.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.aio.portable.swiss.suite.security.authorization.jwt.annotation.JWTAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public abstract class JWTAuthenticationInterceptor extends AnnotationInterceptor {
    private static final String AUTHORIZATION = JWTSugar.HEAD_VALUE_AUTHORIZATION;
    private static final String BEARER = JWTSugar.HEAD_VALUE_BEAR;

    @Autowired
    private JWTTemplate jwtTemplate;

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.getSingleton() : baseBizStatusEnum;
    }

    @Override
    public List<Class<? extends Annotation>> annotations() {
        return Arrays.asList(JWTAuth.class);
    }

    @Override
    protected boolean verify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Method method, Annotation methodAnnotation, Class<?> clazz, Annotation clazzAnnotation) {
        String bearToken = httpServletRequest.getHeader(AUTHORIZATION);
        if (!StringUtils.hasText(bearToken)) {
            throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());
        }

        String token = StringSugar.trimStart(bearToken, BEARER).trim();
        if (this.jwtTemplate.isExpired(token))
            throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), "授权已过期");
        if (!this.jwtTemplate.validate(token))
            throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());

        return true;
    }
}
