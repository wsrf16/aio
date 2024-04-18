package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.filter.hamlet.HamletRepeatableReadRequestFilter;
import org.springframework.stereotype.Component;

@Component
//@WebFilter
public class CustomRepeatableReadRequestFilter extends HamletRepeatableReadRequestFilter {
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        ServletRequest request = servletRequest instanceof HttpServletRequest ?
//                RepeatableHttpServletRequest.repeatable((HttpServletRequest) servletRequest) : servletRequest;
//
//        filterChain.doFilter(request, servletResponse);
//    }
}
