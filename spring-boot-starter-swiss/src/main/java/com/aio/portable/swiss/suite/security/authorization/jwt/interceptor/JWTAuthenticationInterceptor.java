package com.aio.portable.swiss.suite.security.authorization.jwt.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.aio.portable.swiss.suite.security.authorization.jwt.annotation.Granted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public abstract class JWTAuthenticationInterceptor implements HandlerInterceptor {
    private final static String AUTHORIZATION_HEAD = JWTSugar.AUTHORIZATION_HEAD;
    private final static String BEARER = JWTSugar.BEAR_PREFIX;

    @Autowired
    private JWTTemplate jwtTemplate;

    public List<String> getScanBasePackages() {
        return null;
    }

    public Boolean getEnabled() {
        return true;
    }

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.singletonInstance() : baseBizStatusEnum;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        Method method;
        if (handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        } else {
            return true;
        }

        if (getEnabled()) {
            String bearToken = httpServletRequest.getHeader(AUTHORIZATION_HEAD);
            Class<?> declaringClass = method.getDeclaringClass();

            boolean beRequiredPackage = getScanBasePackages() == null || getScanBasePackages().stream()
                    .anyMatch(c -> declaringClass.getPackage().getName().startsWith(StringSugar.trimEnd(c.trim(), ".*")));
            if (beRequiredPackage) {
                boolean required;
                if (method.isAnnotationPresent(Granted.class)) {
                    Granted annotation = method.getAnnotation(Granted.class);
                    required = annotation.required();
                } else if (declaringClass.isAnnotationPresent(Granted.class)) {
                    Granted annotation = declaringClass.getAnnotation(Granted.class);
                    required = annotation.required();
                } else {
                    required = false;
                }

                if (required) {
                    check(bearToken);
                }
            }
            return true;
        } else {
            return true;
        }
    }

    protected void check(String bearToken) {
        if (StringUtils.hasText(bearToken)) {
            String token = StringSugar.trimStart(bearToken, BEARER).trim();
            if (!this.jwtTemplate.validate(token)) {
                if (this.jwtTemplate.isExpired(token))
                    throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), "授权已过期");
                else
                    throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());
            }
        } else
            throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());
    }
}
