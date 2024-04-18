package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.sugar.type.StringSugar;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public abstract class AnnotationInterceptor implements HandlerInterceptor {
    public List<String> scanBasePackages() {
        return null;
    }

    private boolean match(Class<?> clazz) {
        boolean isAttention = scanBasePackages() == null || scanBasePackages().stream()
                .anyMatch(c -> clazz.getPackage().getName().startsWith(StringSugar.trimEnd(c.trim(), ".*")));
        return isAttention;
    }

    protected Boolean getEnabled() {
        return true;
    }

    protected abstract List<Class<? extends Annotation>> annotations();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (this.getEnabled() == null || this.getEnabled()) {
            Method method = ((HandlerMethod) handler).getMethod();
            Class<?> clazz = method.getDeclaringClass();

            boolean matched = match(clazz);
            List<Class<? extends Annotation>> annotations = annotations();
            if (matched && annotations != null) {
                boolean m = annotations.stream().allMatch(c -> verify(c, httpServletRequest, httpServletResponse, handler, method, clazz));
                return m;
            }
        }

        return true;
    }

    private final boolean verify(Class<? extends Annotation> annotation ,HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Method method, Class<?> clazz) {
            Annotation methodAnnotation = method.isAnnotationPresent(annotation) ? method.getAnnotation(annotation) : null;
            Annotation clazzAnnotation = clazz.isAnnotationPresent(annotation) ? clazz.getAnnotation(annotation) : null;
            if (methodAnnotation == null && clazzAnnotation == null)
                return true;
            return verify(httpServletRequest, httpServletResponse, handler, method, methodAnnotation, clazz, clazzAnnotation);
    }

    protected abstract boolean verify(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Method method, Annotation methodAnnotation, Class<?> clazz, Annotation clazzAnnotation);
}
