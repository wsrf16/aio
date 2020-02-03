package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.structure.security.authentication.jwt.RequireToken;
import com.aio.portable.swiss.structure.security.authentication.jwt.JWTAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


//@Configuration
public abstract class HamletAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    JWTAction jwtAction;

    private final static String AUTHORIZATION_HEAD = JWTAction.AUTHORIZATION_HEAD;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        JWTProperties jwtProperties = jwtAction.getJwtProperties();

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
            String token = httpServletRequest.getHeader(AUTHORIZATION_HEAD);
            Class<?> declaringClass = method.getDeclaringClass();

            boolean requiredByPackage = jwtProperties.getBasePackages().stream()
                    .anyMatch(c -> declaringClass.getPackage().getName().startsWith(c.trim()));
            if (requiredByPackage) {
                boolean required;
                if (method.isAnnotationPresent(RequireToken.class)) {
                    RequireToken annotation = method.getAnnotation(RequireToken.class);
                    required = annotation.required();
                } else if (declaringClass.isAnnotationPresent(RequireToken.class)) {
                    RequireToken annotation = declaringClass.getAnnotation(RequireToken.class);
                    required = annotation.required();
                } else {
                    required = false;
                }

                if (required) {
                    if (!StringUtils.hasText(token) ||
                            !this.jwtAction.verify(token))
                        throw new BizException(BizStatusEnum.UNAUTHORIZED.getCode(), BizStatusEnum.UNAUTHORIZED.getMessage());
                }
            }
            return true;
        }
    }
}
