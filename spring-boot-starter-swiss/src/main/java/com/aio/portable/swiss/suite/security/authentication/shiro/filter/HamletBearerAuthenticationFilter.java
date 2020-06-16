package com.aio.portable.swiss.suite.security.authentication.shiro.filter;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BearerHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

//@Component
public class HamletBearerAuthenticationFilter extends BearerHttpAuthenticationFilter {

    boolean unauthorizedRedirectToLogin = false;
    boolean unauthorizedRedirectToLoginWithQueryString = true;

    /**
     * 判断token有效性
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean isLoginRequest = false;
        // 判断为登录请求，即请求头是否带"token"
        // 如果请求头不存在 token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 false，以跳转到登录页
        // 也可以直接抛出异常，而不跳转到登录页
        boolean existAuthorizationHeader = isLoginRequest(request, response);
        if (existAuthorizationHeader) {
            //如果为登录请求，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                // -> subject.securityManager.login(this, token) -> 跳到doGetAuthenticationInfo
                isLoginRequest = this.executeLogin(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                isLoginRequest = false;
            }
        }
        return isLoginRequest;
    }

//    @Override
//    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException authEx, ServletRequest request, ServletResponse response) {
//        return false;
//    }

    /**
     * 上面的方法如果返回false,则接下来会执行这个方法,如果返回为true,则不会执行这个方法
     * 判断是否为登录url,进一步判断请求是不是post
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String loginUrl = this.getLoginUrl();
        String queryString = ((HttpServletRequest) request).getQueryString();

        boolean isLoginUrl = Objects.equals(requestURI, loginUrl);
        if (isLoginUrl) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
//            throw new BizException(BizStatusEnum.UNAUTHORIZED.getCode(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } else {
            if (unauthorizedRedirectToLogin) {
                String redirectLoginUrl;
                if (unauthorizedRedirectToLoginWithQueryString) {
                    redirectLoginUrl = UriComponentsBuilder.fromUriString(loginUrl).query(queryString).build().toString();
                    redirectToLogin(request, response, redirectLoginUrl);
                }
                else {
                    redirectLoginUrl = loginUrl;
                    redirectToLogin(request, response, redirectLoginUrl);
                }
                return false;
            } else
                throw new AuthenticationException(HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
    }

    protected void redirectToLogin(ServletRequest request, ServletResponse response, String loginUrl) throws IOException {
        WebUtils.issueRedirect(request, response, loginUrl);
    }

}