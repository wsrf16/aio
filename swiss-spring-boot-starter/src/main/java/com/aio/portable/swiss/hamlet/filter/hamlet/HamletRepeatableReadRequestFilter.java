package com.aio.portable.swiss.hamlet.filter.hamlet;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.suite.io.servlet.RepeatableReadHttpServletRequest;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public abstract class HamletRepeatableReadRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = null;
        if (this.match(servletRequest, servletResponse, filterChain) && servletRequest instanceof HttpServletRequest) {
            request = RepeatableReadHttpServletRequest.repeatable((HttpServletRequest) servletRequest);
        } else {
            request = servletRequest;
        }
        filterChain.doFilter(request, servletResponse);
    }

    protected boolean match(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getContentType() == null || CollectionSugar.containsAny(servletRequest.getContentType(), MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE)) {
            return false;
        } else
            return true;
    }
}
