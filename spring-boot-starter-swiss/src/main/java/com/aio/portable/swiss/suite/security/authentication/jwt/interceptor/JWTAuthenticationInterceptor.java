package com.aio.portable.swiss.suite.security.authentication.jwt.interceptor;

import com.aio.portable.swiss.hamlet.bean.BizStatusNativeEnum;
import com.aio.portable.swiss.factories.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTAction;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public abstract class JWTAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    JWTSession jwtSession;

    public abstract List<String> getScanBasePackages();

    private final static String AUTHORIZATION_HEAD = JWTAction.AUTHORIZATION_HEAD;
    private final String bearer = JWTAction.BEAR_PREFIX;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        JWTProperties jwtProperties = jwtSession.getJwtProperties();

        Method method;
        if (!(object instanceof HandlerMethod)) {
            return true;
        } else {
            HandlerMethod handlerMethod = (HandlerMethod) object;
            method = handlerMethod.getMethod();
        }

        if (jwtProperties.getRequire() == false) {
            return true;
        } else {
            String bearToken = httpServletRequest.getHeader(AUTHORIZATION_HEAD);
            Class<?> declaringClass = method.getDeclaringClass();

            boolean requiredByPackage = getScanBasePackages().stream()
                    .anyMatch(c -> declaringClass.getPackage().getName().startsWith(c.trim()));
            if (requiredByPackage) {
                boolean required;
                if (method.isAnnotationPresent(Credentials.class)) {
                    Credentials annotation = method.getAnnotation(Credentials.class);
                    required = annotation.required();
                } else if (declaringClass.isAnnotationPresent(Credentials.class)) {
                    Credentials annotation = declaringClass.getAnnotation(Credentials.class);
                    required = annotation.required();
                } else {
                    required = false;
                }

                if (required && StringUtils.hasText(bearToken)) {
                    String token = StringSugar.removeStart(bearToken, bearer);
                    if (!this.jwtSession.validate(token))
                        throw new BizException(BizStatusNativeEnum.staticUnauthorized().getCode(), BizStatusNativeEnum.staticUnauthorized().getMessage());
                }
            }
            return true;
        }
    }
}
