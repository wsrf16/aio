package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.suite.io.servlet.RepeatableHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public abstract class HamletRepeatableRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = servletRequest instanceof HttpServletRequest ?
                RepeatableHttpServletRequest.repeatable((HttpServletRequest) servletRequest) : servletRequest;

        filterChain.doFilter(request, servletResponse);
    }

}
