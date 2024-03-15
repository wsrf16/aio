package com.aio.portable.park.intercept;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.common.BizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.Swagger3Properties;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Configuration
public class CustomHandlerInterceptor implements HandlerInterceptor {
    static LogHub log = AppLogHubFactory.staticBuild();
    @Autowired(required = false)
    Swagger3Properties swagger3Properties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String uri = request.getRequestURI();
        log.i("request uri", uri);

        Boolean checkSwagger = checkSwagger(request, response);
        if (Objects.equals(checkSwagger, true))
            return checkSwagger;
        else if (Objects.equals(checkSwagger, false)) {
//            throw new BizException(BizStatusEnum.staticUnauthorized().getCode(), "No found page.");
            unauthorized(response);
        }

        return true;
    }

    private static final void unauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().print(ResponseWrappers.unauthorized());
    }

    private Boolean checkSwagger(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        if (uri.contains("/swagger-resources") || uri.contains("/api-docs") || uri.contains("/doc.html") || uri.contains("/webjars"))
            if (swaggerIsDisabled())
                return false;
            else
                return true;
        return null;
    }

    private boolean swaggerIsDisabled() {
        return swagger3Properties == null || swagger3Properties.getEnabled() != true;
    }
}
