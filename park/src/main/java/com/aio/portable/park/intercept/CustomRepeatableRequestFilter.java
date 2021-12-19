package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.HamletRepeatableRequestFilter;
import com.aio.portable.swiss.suite.io.servlet.RepeatableHttpServletRequest;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter
public class CustomRepeatableRequestFilter extends HamletRepeatableRequestFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest request = servletRequest instanceof HttpServletRequest ?
                RepeatableHttpServletRequest.repeatable((HttpServletRequest) servletRequest) : servletRequest;

        filterChain.doFilter(request, servletResponse);
    }
}
