package com.aio.portable.swiss.suite.security.authorization.jwt.interceptor;

import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.annotation.Granted;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AuthenticationInterceptor implements HandlerInterceptor {
    public List<String> getScanBasePackages() {
        return null;
    }

    public Boolean getEnabled() {
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        Method method;
        if (handler instanceof HandlerMethod) {
            method = ((HandlerMethod) handler).getMethod();
        } else {
            return true;
        }

        if (this.getEnabled()) {
            Class<?> clazz = method.getDeclaringClass();

            boolean beRequiredPackage = getScanBasePackages() == null || getScanBasePackages().stream()
                    .anyMatch(c -> clazz.getPackage().getName().startsWith(StringSugar.trimEnd(c.trim(), ".*")));
            if (beRequiredPackage) {
                boolean required;
                if (method.isAnnotationPresent(Granted.class)) {
                    required = method.getAnnotation(Granted.class).required();
                } else if (clazz.isAnnotationPresent(Granted.class)) {
                    required = clazz.getAnnotation(Granted.class).required();
                } else {
                    required = false;
                }

                if (required) {
                    check(httpServletRequest, httpServletResponse, handler);
                }
            }
        }
        return true;
    }

    protected abstract void check(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);
}
