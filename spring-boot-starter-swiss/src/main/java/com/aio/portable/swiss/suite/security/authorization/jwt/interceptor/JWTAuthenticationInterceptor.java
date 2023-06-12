package com.aio.portable.swiss.suite.security.authorization.jwt.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class JWTAuthenticationInterceptor extends AuthenticationInterceptor {
    private static final String AUTHORIZATION = JWTSugar.HEAD_VALUE_AUTHORIZATION;
    private static final String BEARER = JWTSugar.HEAD_VALUE_BEAR;

    @Autowired
    private JWTTemplate jwtTemplate;

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.getSingleton() : baseBizStatusEnum;
    }


    protected void check(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
        String bearToken = httpServletRequest.getHeader(AUTHORIZATION);
        if (!StringUtils.hasText(bearToken)) {
            throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());
        }

        String token = StringSugar.trimStart(bearToken, BEARER).trim();
        if (!this.jwtTemplate.validate(token)) {
            if (this.jwtTemplate.isExpired(token))
                throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), "授权已过期");
            else
                throw new BizException(getBizStatusEnum().staticUnauthorized().getCode(), getBizStatusEnum().staticUnauthorized().getMessage());
        }
    }
}
