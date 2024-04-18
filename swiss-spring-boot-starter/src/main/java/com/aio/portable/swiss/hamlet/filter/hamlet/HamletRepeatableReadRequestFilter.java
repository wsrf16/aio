package com.aio.portable.swiss.hamlet.filter.hamlet;

import com.aio.portable.swiss.suite.io.servlet.RepeatableReadHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter
public abstract class HamletRepeatableReadRequestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = servletRequest instanceof HttpServletRequest ?
                RepeatableReadHttpServletRequest.repeatable((HttpServletRequest) servletRequest) : servletRequest;

        filterChain.doFilter(request, servletResponse);
    }
}
