package com.aio.portable.swiss.suite.security.authentication;

import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplateType;
import com.auth0.jwt.JWTCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    //    private static final String ISSURE = "ladder";
    private static final String AUTHORIZATION_HEAD = JWTSugar.HEAD_VALUE_AUTHORIZATION;

    @Autowired
    private JWTTemplateType jwtTemplate;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();

        JWTCreator.Builder builder = jwtTemplate.createBuilder();
        String token = jwtTemplate.sign(builder);
        httpServletResponse.setHeader(AUTHORIZATION_HEAD, token);
    }
}
